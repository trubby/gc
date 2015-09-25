package Trubby.co.th;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.WorldServer;

public class CarListener implements Listener {

	@EventHandler
	public void onLeave(PlayerToggleSneakEvent e){
		breakTheCar(e.getPlayer());
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent e){
		if(riding.containsKey(e.getPlayer().getUniqueId())){
			breakTheCar(e.getPlayer());
		}
	}
	
	public void breakTheCar(Player p){
		if(riding.containsKey(p.getUniqueId())){
			
			ArmorStand as = (ArmorStand) riding.get(p.getUniqueId()).getBukkitEntity();
			
			if(p.getInventory().firstEmpty() == -1){
				p.getWorld().dropItem(p.getLocation(), as.getHelmet());
			}else{
				p.getInventory().addItem(as.getHelmet());
				p.updateInventory();
			}
			riding.get(p.getUniqueId()).getBukkitEntity().remove();
			riding.remove(p.getUniqueId());
			
		}
	}
	
	public void breakAllCar(){
		for(UUID uuid : riding.keySet()){
			breakTheCar(Bukkit.getPlayer(uuid));
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		if(riding.containsKey(e.getEntity().getUniqueId())){
			breakTheCar(e.getEntity());
		}
	}
	
	@EventHandler
	public void onArmorstandInteract(PlayerArmorStandManipulateEvent e){
		if(carList.contains(e.getRightClicked().getHelmet().getType())){
			e.setCancelled(true);
		}
	}
	
	ArrayList<Material> carList = new ArrayList<>();
	HashMap<UUID, PigEdit> riding = new HashMap<>();
	
	public CarListener() {
		Collections.addAll(carList, Material.COAL_ORE);
	}
	
	@EventHandler
	public void onPlayerCar(PlayerInteractEvent e){
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(carList.contains(e.getPlayer().getItemInHand().getType())){
				e.setCancelled(true);
				Player p = e.getPlayer();
				
				if(p.getVehicle() != null){
					if(p.getVehicle().getType() == EntityType.ARMOR_STAND){
						p.sendMessage("You're already riding.");
						return;
					}
				}
				WorldServer nmsWorld = ((CraftWorld) p.getLocation().getWorld()).getHandle();
				PigEdit pE = new PigEdit(nmsWorld);
				//pE.setPosition(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
				pE.setPositionRotation(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
				ItemStack car = p.getItemInHand();
				car.setAmount(1);
				pE.setEquipment(4, CraftItemStack.asNMSCopy(car));
				pE.setInvisible(true);
				nmsWorld.addEntity(pE);
				pE.getBukkitEntity().setPassenger(p);
				
				if(riding.containsKey(p.getUniqueId())){
					riding.get(p.getUniqueId()).getBukkitEntity().remove();
				}else{
					riding.put(p.getUniqueId(), pE);
				}
				ItemStack is = p.getItemInHand();
				if(is.getAmount() > 1){
				    is.setAmount(is.getAmount() - 1);
				}else{ 
					p.setItemInHand(null);
					p.updateInventory();
				}
			}
		}
	}
	
}
