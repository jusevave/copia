package es.webbeta.serializer;

import java.io.File;

public class CacheMetadataAccessor extends FileMetadataAccessor implements IMetadataAccessor {

    private static final String KEY_TPL = "serializer_metadata___%s";

    private ICache cache;

    public CacheMetadataAccessor(ICache cache) {
        super();

        this.cache = cache;
    }

    private String generateKey(Class<?> klass) {
        return generateKey(klass.getCanonicalName());
    }

    private String generateKey(String canonical) {
        return String.format(KEY_TPL, canonical);
    }

    private String getFilenameWithoutExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    @Override
    public Boolean hasMetadata(Class klass) {
        return super.hasMetadata(klass);
    }

    @Override
    public String getMetadataContent(Class klass) {
        String key = generateKey(klass);
        if (cache.get(key) != null)
            return cache.get(key);
        else {
            if (super.hasMetadata(klass)) {
                String metadata = super.getMetadataContent(klass);
                cache.set(key, metadata);
                return metadata;
            } else
                return null;
        }
    }

    /**
     * Removes all cache entries
     */
    public void clearMetadataCache() {
        File dir = this.metadataPath.toFile();
        if (dir == null) return;
        File[] files = dir.listFiles();
        if (files == null) return;

        for (final File fileEntry : files) {
            if (!fileEntry.isFile()) continue;

            String canonical = getFilenameWithoutExtension(fileEntry.getName());
            String cacheKey = generateKey(canonical);

            if (cache.get(cacheKey) != null)
                cache.remove(cacheKey);
        }
    }

}
