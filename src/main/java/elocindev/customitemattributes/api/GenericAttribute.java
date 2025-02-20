package elocindev.customitemattributes.api;

import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class GenericAttribute<A, V> {
    private A attribute;
    private V value;
    private String operation;
    private String id;

    public GenericAttribute(A attribute, V value, String operation, String id) {
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
        this.id = id;
    }

    public RegistryEntry<EntityAttribute> getAttribute() throws InvalidAttributeException {
        if (attribute instanceof String attributeId) {
            RegistryEntry<EntityAttribute> entAttribute = Registries.ATTRIBUTE.getEntry(Identifier.of(attributeId))
                    .orElseThrow(() -> new InvalidAttributeException("Attribute not found: " + attribute));

            return entAttribute;
        }

        throw new InvalidAttributeException("Attribute not found: " + attribute);
    }

    public String getString() throws InvalidAttributeException {
        if (attribute instanceof String attributeName) {
            return attributeName;
        }

        throw new InvalidAttributeException("Invalid Type: " + attribute + " must be String");
    }

    public Operation getOperation() {
        return Operation.CODEC.decode(JsonOps.INSTANCE, new JsonPrimitive(operation))
                .getOrThrow()
                .getFirst();
    }

    public double getDouble() throws InvalidAttributeException {
        if (value instanceof Double doubleValue) {
            return doubleValue;
        }

        throw new InvalidAttributeException("Invalid Type: " + value+" must be Double");
    }

    public Identifier getId() {
        return Identifier.of(id);
    }

    public V getValue() {
        return value;
    }
}
