package elocindev.customitemattributes.api;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class GenericAttribute<A, V> {
    private A attribute;
    private V value;
    private String operation;

    public GenericAttribute(A attribute, V value, String operation) {
        this.attribute = attribute;
        this.value = value;

        if (operation.toUpperCase() == "ADDITION" || operation.toUpperCase() == "MULTIPLY_BASE" || operation.toUpperCase() == "MULTIPLY_TOTAL") {
            this.operation = operation.toUpperCase();
        } else {
            this.operation = "ADDITION";
        }
    }

    public EntityAttribute getAttribute() throws InvalidAttributeException {
        if (attribute instanceof String attributeId) {
            EntityAttribute entAttribute = Registries.ATTRIBUTE.get(new Identifier(attributeId));

            if (entAttribute != null) 
                return entAttribute;
        }

        throw new InvalidAttributeException("Attribute not found: " + attribute);
    }

    public String getString() throws InvalidAttributeException {
        if (attribute instanceof String) {
            return (String) attribute;
        }

        throw new InvalidAttributeException("Invalid Type: " + attribute+" must be String");
    }

    public Operation getOperation() {
        switch (this.operation) {
            case "ADDITION":
                return Operation.ADDITION;
            case "MULTIPLY_BASE":
                return Operation.MULTIPLY_BASE;
            case "MULTIPLY_TOTAL":
                return Operation.MULTIPLY_TOTAL;
        }

        return Operation.ADDITION;
    }

    public double getDouble() throws InvalidAttributeException {
        if (value instanceof Double) {
            return (Double) value;
        }

        throw new InvalidAttributeException("Invalid Type: " + value+" must be Double");
    }

    public V getValue() {
        return value;
    }
}
