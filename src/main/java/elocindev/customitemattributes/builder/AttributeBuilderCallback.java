package elocindev.customitemattributes.builder;

import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import elocindev.customitemattributes.CustomItemAttributes;
import elocindev.customitemattributes.api.GenericAttribute;
import elocindev.customitemattributes.api.ItemProperty;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributeBuilderCallback {
    public static void register() {
        // run this after most other mods
        Identifier phase = Identifier.of(CustomItemAttributes.MOD_ID, "after_default");
        DefaultItemComponentEvents.MODIFY.addPhaseOrdering(Event.DEFAULT_PHASE,phase );

        DefaultItemComponentEvents.MODIFY.register(phase, modifyContext -> {
            Map<Item, List<ItemProperty>> itemsToProperties = CustomItemAttributes.CONFIG.items.stream()
                    .filter(property -> property.getItem() != null)
                    .collect(
                            Collectors.groupingBy(
                                    ItemProperty::getItem,
                                    IdentityHashMap::new,
                                    Collectors.toList()
                            )
                    );

            modifyContext.modify(itemsToProperties::containsKey, (builder, item) -> {
                        List<ItemProperty> properties = itemsToProperties.getOrDefault(item, Collections.emptyList());
                        if (!properties.isEmpty()) {
                            modifyItemComponents(builder, item, properties);
                        }
                    }
            );
        });
    }

    private static void modifyItemComponents(ComponentMap.Builder builder, Item item, List<ItemProperty> properties) {
        AttributeModifiersComponent component = builder.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS,
                AttributeModifiersComponent.DEFAULT
        );

        if (component.modifiers().isEmpty()) {
            // this is where the default attributes of armor is stored for some god forsaken reason
            component = item.getAttributeModifiers();
        }

        for (ItemProperty property : properties) {
            if (property.unbreakable || property.shouldForceUnbreakable()) {
                UnbreakableComponent unbreakableComponent = builder.getOrCreate(
                        DataComponentTypes.UNBREAKABLE,
                        () -> new UnbreakableComponent(true)
                );
                builder.add(DataComponentTypes.UNBREAKABLE, unbreakableComponent);
            }

            component = applyModifiers(component, property);
        }

        builder.add(DataComponentTypes.ATTRIBUTE_MODIFIERS, component);
    }

    private static AttributeModifiersComponent applyModifiers(AttributeModifiersComponent component, ItemProperty property) {
        for (GenericAttribute<?, ?> generic_attribute : property.attribute_overrides) {
            try {
                RegistryEntry<EntityAttribute> attribute = generic_attribute.getAttribute();

                for (String slotID : property.getSlotNames()) {
                    AttributeModifierSlot slot = AttributeModifierSlot.CODEC.decode(JsonOps.INSTANCE, new JsonPrimitive(slotID))
                            .getOrThrow()
                            .getFirst();
                    component = component.with(
                            attribute,
                            new EntityAttributeModifier(
                                    generic_attribute.getId(),
                                    generic_attribute.getDouble(),
                                    generic_attribute.getOperation()
                            ),
                            slot
                    );
                }
            } catch (Exception e) {
                CustomItemAttributes.LOGGER.error("Error adding attribute modifier: {}", e.getMessage());
            }
        }

        return component;
    }
}
