package elocindev.customitemattributes.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import elocindev.customitemattributes.CustomItemAttributes;
import elocindev.customitemattributes.api.GenericAttribute;
import elocindev.customitemattributes.api.ItemProperty;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class AttributeBuilderCallback {
    public static void register() {
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, builder) -> {
            for (ItemProperty item : CustomItemAttributes.CONFIG.items) {
                if (item.getItem() != stack.getItem()) continue;

                NbtCompound nbt = stack.getNbt();
                if (nbt != null && nbt.getBoolean("Unbreakable") != item.unbreakable) {
                    nbt.putBoolean("Unbreakable", item.unbreakable);
                    stack.writeNbt(nbt);
                }

                switch(slot) {
                    case MAINHAND:
                        applyModifiers(stack, item, item.overrides_main_hand, builder);
                        break;
                    case OFFHAND:
                        applyModifiers(stack, item, item.overrides_off_hand, builder);
                        break;
                    case HEAD:
                        applyModifiers(stack, item, item.overrides_head, builder);
                        break;
                    case CHEST:
                        applyModifiers(stack, item, item.overrides_chest, builder);
                        break;
                    case LEGS:
                        applyModifiers(stack, item, item.overrides_legs, builder);
                        break;
                    case FEET:
                        applyModifiers(stack, item, item.overrides_feet, builder);
                        break;
                }
            }
        });
    }

    public static void applyModifiers(ItemStack stack, ItemProperty property, List<GenericAttribute<String, ?>> attributes, Multimap<EntityAttribute, EntityAttributeModifier> builder) {        
        for (GenericAttribute<?, ?> generic_attribute : attributes) {
            try {
                EntityAttribute attribute = generic_attribute.getAttribute();
                Collection<EntityAttributeModifier> newModifiers = new ArrayList<>();              

                newModifiers.add(
                    new EntityAttributeModifier(
                        UUID.nameUUIDFromBytes(generic_attribute.getString().getBytes()), 
                        "Custom Item Attributes Modifier", 
                        generic_attribute.getDouble(), 
                        generic_attribute.getOperation()
                    ));
    
                if (builder.containsValue(attribute))
                    builder.replaceValues(attribute, newModifiers);
                else 
                    builder.putAll(attribute, newModifiers);
    
            } catch (Exception e) {
                CustomItemAttributes.LOGGER.error("Error adding attribute modifier: " + e.getMessage());
            }
        }
    }
}
