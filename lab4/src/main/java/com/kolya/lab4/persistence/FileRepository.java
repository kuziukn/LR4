package com.kolya.lab4.persistence;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class FileRepository<E extends Serializable> implements Repository<E> {

    private final File directory;
    private final File metadata;
    private long maxId;

    public FileRepository(Path pathToDirectory) throws IOException {

        directory = pathToDirectory.toFile();
        metadata = new File(directory, "meta.dat");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!metadata.exists()) {
            if (!metadata.createNewFile()) {
                throw new IOException("Unable to create file");
            }
            maxId = 0;
            updateMetadata(maxId);
            return;
        }

        maxId = readMetadata();
    }


    @Override
    public Entry<E> create(E element) {

        if (element == null) {
            throw new NullPointerException();
        }

        String filename = getFileName(maxId+1);

        File newFile = new File(directory, filename);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newFile))){
            oos.writeObject(element);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        maxId++;

        updateMetadata(maxId);
        return new Entry<>(maxId, element);

    }

    @Override
    public Optional<Entry<E>> get(Long id) {

        File file = new File(directory, getFileName(id));

        if (!file.exists()) {
            return Optional.empty();
        }
        E object;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            object = (E) objectInputStream.readObject();
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(new Entry<>(id, object));
    }

    private String getFileName(Long id) {
        return id + ".obj";
    }

    private Long readMetadata() {
        long id;
        try {
            Scanner scanner = new Scanner(metadata);
            id = scanner.nextLong();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    private void updateMetadata(Long id) {

        try (FileWriter writer = new FileWriter(metadata)) {
            writer.flush();
            writer.write(id.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
