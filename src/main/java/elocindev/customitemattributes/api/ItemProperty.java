package elocindev.customitemattributes.api;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemProperty {
    public ItemProperty(String itemid, 
            List<GenericAttribute<String,?>> overrides_main_hand,
            List<GenericAttribute<String,?>> overrides_off_hand,
            List<GenericAttribute<String,?>> overrides_head,
            List<GenericAttribute<String,?>> overrides_chest,
            List<GenericAttribute<String,?>> overrides_legs,
            List<GenericAttribute<String,?>> overrides_feet,            
    boolean isUnbreakable) {
        this.item = itemid;
        
        this.overrides_head = overrides_head;
        this.overrides_chest = overrides_chest;
        this.overrides_legs = overrides_legs;
        this.overrides_feet = overrides_feet;
        this.overrides_main_hand = overrides_main_hand;
        this.overrides_off_hand = overrides_off_hand;

        this.unbreakable = isUnbreakable;
    }

    public Item getItem() {
        return Registries.ITEM.get(new Identifier(item));
    }

    public Identifier getIdentifier() {
        return new Identifier(item);
    }

    public String item;

    public List<GenericAttribute<String,?>> overrides_main_hand;
    public List<GenericAttribute<String,?>> overrides_off_hand;
    public List<GenericAttribute<String,?>> overrides_head;
    public List<GenericAttribute<String,?>> overrides_chest;
    public List<GenericAttribute<String,?>> overrides_legs;
    public List<GenericAttribute<String,?>> overrides_feet;

    public boolean unbreakable;
}
