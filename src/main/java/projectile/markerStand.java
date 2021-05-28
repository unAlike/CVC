package projectile;

import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.Vector3f;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.EulerAngle;


public class markerStand extends EntityArmorStand {


    public markerStand(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.setInvisible(true);
        this.setInvulnerable(true);
        this.setMarker(true);
        this.setArms(true);
        this.setRightArmPose(new Vector3f(0,45,50));
        this.setLeftArmPose(new Vector3f(0,45,-50));
    }
    public void setTicksLived(int i){
        this.ticksLived = i;
    }
}
