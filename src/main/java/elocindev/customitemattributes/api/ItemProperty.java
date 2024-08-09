package elocindev.customitemattributes.api;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemProperty {
    public String item;
    public List<String> affected_slots;
    public List<GenericAttribute<String,?>> attribute_overrides;
    public boolean unbreakable;
    public boolean force_unbreakable = false;

    public ItemProperty(
        String item_id,
        List<String> slot_names,
        List<GenericAttribute<String,?>> attribute_overrides        
    ) {
        this.item = item_id;
        this.attribute_overrides = attribute_overrides;
        this.affected_slots = slot_names;
    }

    public ItemProperty(
        String item_id,
        List<String> slot_names,
        List<GenericAttribute<String,?>> attribute_overrides,         
        boolean isUnbreakable
    ) {
        this.item = item_id;
        this.affected_slots = slot_names;
        
        this.attribute_overrides = attribute_overrides;

        this.unbreakable = isUnbreakable;
        this.force_unbreakable = true;
    }

    public ItemProperty(
        String item_id,
        List<String> slot_names,
        List<GenericAttribute<String,?>> attribute_overrides,         
        boolean isUnbreakable,
        boolean force_unbreakable
    ) {
        this.item = item_id;
        this.affected_slots = slot_names;
        
        this.attribute_overrides = attribute_overrides;

        this.unbreakable = isUnbreakable;
        this.force_unbreakable = force_unbreakable;
    }

    public Item getItem() {
        return Registries.ITEM.get(new Identifier(item));
    }

    public List<String> getSlotNames() {
        return affected_slots;
    }

    public Identifier getIdentifier() {
        return new Identifier(item);
    }

    public boolean shouldForceUnbreakable() {
        return force_unbreakable;
    }
}
