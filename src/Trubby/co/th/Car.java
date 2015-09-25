package Trubby.co.th;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import Trubby.co.th.Particle.ParticleEffect;
import net.minecraft.server.v1_8_R3.WorldServer;

public class Car extends JavaPlugin {
	
	CarListener cl;
	
	@Override
	public void onEnable() {
		cl = new CarListener();
		Bukkit.getPluginManager().registerEvents(cl, this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(PigEdit pe : cl.riding.values()){
					Vector vec = pe.getBukkitEntity().getLocation().getDirection().multiply(-1.75D);
					Location location = vec.toLocation(pe.getBukkitEntity().getWorld()).add(pe.getBukkitEntity().getLocation());
			        location.add(0.0D, 1.4D, 0.0D);
			        ParticleEffect.CLOUD.display(0, 0, 0, 0, 1, location, 20);
				}
				
			}
		}, 2L, 2L);
		
	}
	
	@Override
	public void onDisable() {
		cl.breakAllCar();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("test131231")) {
			new NMSUtil().registerEntity("Kart", 90, PigEdit.class, PigEdit.class);
			sender.sendMessage(":D");

			Player p = (Player) sender;
			ArmorStand as = (ArmorStand) p.getWorld().spawn(p.getLocation(), ArmorStand.class);
			as.setPassenger(p);
		} else if (label.equalsIgnoreCase("test2")) {
			Player p = (Player) sender;
			WorldServer nmsWorld = ((CraftWorld) p.getLocation().getWorld()).getHandle();
			PigEdit pE = new PigEdit(nmsWorld);
			pE.setPosition(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
			pE.setEquipment(4, CraftItemStack.asNMSCopy(new ItemStack(Material.COAL_ORE)));
			pE.setInvisible(true);

			nmsWorld.addEntity(pE);
			Bukkit.broadcastMessage("Done.");
			pE.getBukkitEntity().setPassenger(p);
		}
		return false;
	}
}
