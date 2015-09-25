package Trubby.co.th;

import org.bukkit.entity.Vehicle;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Vector3f;
import net.minecraft.server.v1_8_R3.World;

public class PigEdit extends EntityArmorStand{
	public PigEdit(World world) {
		super(world);
	}

	public void g(float sideMot, float forMot) {
		if ((this.passenger != null) && ((this.passenger instanceof EntityHuman))) {
			this.aI = (this.aG = this.yaw);
			sideMot = ((EntityLiving) this.passenger).aZ * 0.5F;
			if (sideMot < 0.0F) {
				turnRight();
			} else if (sideMot > 0.0F) {
				turnLeft();
			} else if (sideMot == 0.0F) {
				normalize();
			}
			forMot = ((EntityLiving) this.passenger).ba;
			if (forMot <= 0.0F) {
				forMot *= 0.25F;
			}
			if ((!this.world.isClientSide) && (au()) && ((this.vehicle instanceof EntityLiving))
					&& (!(this.vehicle instanceof EntityArmorStand))) {
				mount(null);
			}
			this.S = 1.0F;
			this.aM = (bI() * 0.1F);
			if (!this.world.isClientSide) {
				k(0.35F);
				super.g(0.0F, forMot);
			}
			this.ay = this.az;
			double d0 = this.locX - this.lastX;
			double d1 = this.locZ - this.lastZ;
			float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
			if (f4 > 1.0F) {
				f4 = 1.0F;
			}
			this.az += (f4 - this.az) * 0.4F;
			this.aA += this.az;
		} else {
			this.S = 0.5F;
			this.aK = 0.02F;
			super.g(sideMot, forMot);
		}
	}

	float first = 6.0F;
	float second = 4.0F;
	float third = 2.0F;

	public void normalize() {
		float headZ = this.headPose.getZ();
		if (headZ < 0.0F) {
			if (headZ <= -30.0F) {
				setYawPitch(this.yaw + this.first, this.pitch);
			} else if (headZ <= -15.0F) {
				setYawPitch(this.yaw + this.second, this.pitch);
				setHeadPose(new Vector3f(0.0F, 0.0F, headZ - 5.0F));
			} else if (headZ <= -5.0F) {
				setYawPitch(this.yaw + this.third, this.pitch);
				setHeadPose(new Vector3f(0.0F, 0.0F, headZ - 5.0F));
			}
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ + 5.0F));
		} else if (headZ > 0.0F) {
			if (headZ >= 30.0F) {
				setYawPitch(this.yaw - this.first, this.pitch);
			} else if (headZ >= 15.0F) {
				setYawPitch(this.yaw - this.second, this.pitch);
				setHeadPose(new Vector3f(0.0F, 0.0F, headZ + 5.0F));
			} else if (headZ >= 5.0F) {
				setYawPitch(this.yaw - this.third, this.pitch);
				setHeadPose(new Vector3f(0.0F, 0.0F, headZ + 5.0F));
			}
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ - 5.0F));
		}
	}

	public void turnRight() {
		float headZ = this.headPose.getZ();
		if (headZ <= -30.0F) {
			setYawPitch(this.yaw + this.first, this.pitch);
		} else if (headZ <= -15.0F) {
			setYawPitch(this.yaw + this.second, this.pitch);
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ - 5.0F));
		} else if (headZ <= -5.0F) {
			setYawPitch(this.yaw + this.third, this.pitch);
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ - 5.0F));
		} else {
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ - 5.0F));
		}
	}

	public void turnLeft() {
		float headZ = this.headPose.getZ();
		if (headZ >= 30.0F) {
			setYawPitch(this.yaw - this.first, this.pitch);
		} else if (headZ >= 15.0F) {
			setYawPitch(this.yaw - this.second, this.pitch);
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ + 5.0F));
		} else if (headZ >= 5.0F) {
			setYawPitch(this.yaw - this.third, this.pitch);
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ + 5.0F));
		} else {
			setHeadPose(new Vector3f(0.0F, 0.0F, headZ + 5.0F));
		}
	}
}
