package es.webbeta.serializer.metadata;

import es.webbeta.serializer.type.FieldAccessType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Metadata {

    private String canonicalName;
    private FieldAccessType accessType;
    private List<MetadataVirtualProperty> virtualProperties = new ArrayList<>();
    private List<MetadataProperty> properties = new ArrayList<>();

    Metadata(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public Boolean appliesTo(String canonicalName) {
        return this.canonicalName.equals(canonicalName);
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public Boolean hasAccessType() {
        return accessType != null;
    }

    public FieldAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(FieldAccessType accessType) {
        this.accessType = accessType;
    }

    public Boolean hasVirtualProperties() {
        return virtualProperties.size() > 0;
    }

    public List<MetadataVirtualProperty> getVirtualProperties() {
        return virtualProperties;
    }

    public Boolean hasVirtualProperty(String name) {
        return getVirtualProperty(name) != null;
    }

    public MetadataVirtualProperty getVirtualProperty(String name) {
        List<MetadataVirtualProperty> props = virtualProperties.stream()
                .filter(prop -> prop.getPropertyName().equals(name))
                .collect(Collectors.toList());

        if (props.size() == 0)
            return null;
        else
            return props.get(0);
    }

    public void setVirtualProperties(List<MetadataVirtualProperty> virtualProperties) {
        this.virtualProperties = virtualProperties;
    }

    public Boolean hasProperties() {
        return properties.size() > 0;
    }

    public List<MetadataProperty> getProperties() {
        return properties;
    }

    public Boolean hasProperty(String name) {
        return getProperty(name) != null;
    }

    public MetadataProperty getProperty(String name) {
        List<MetadataProperty> props = properties.stream()
                .filter(prop -> prop.getPropertyName().equals(name))
                .collect(Collectors.toList());

        if (props.size() == 0)
            return null;
        else
            return props.get(0);
    }

    public void setProperties(List<MetadataProperty> properties) {
        this.properties = properties;
    }

    public Boolean hasMixedProperties() {
        return hasProperties() || hasVirtualProperties();
    }

    public List<IMetadataProperty> getMixedProperties() {
        return Stream.concat(properties.stream(), virtualProperties.stream()).collect(Collectors.toList());
    }

    public Boolean hasMixedProperty(String name) {
        return getMixedProperty(name) != null;
    }

    public IMetadataProperty getMixedProperty(String name) {
        List<IMetadataProperty> mixedProps = getMixedProperties();

        List<IMetadataProperty> props = mixedProps.stream()
                .filter(prop -> prop.getPropertyName().equals(name))
                .collect(Collectors.toList());

        if (props.size() == 0)
            return null;
        else
            return props.get(0);
    }

}
