package erebus.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPunchshroom extends EntityMob {
	private int shroomJumpDelay;
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;

	public EntityPunchshroom(World par1World) {

		super(par1World);
		isImmuneToFire = true;
		setSize(1.0F, 1.0F);
		shroomJumpDelay = rand.nextInt(20) + 10;
		tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.0D, false));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, new Integer(0));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
	}

	/*
	 * @Override protected String getLivingSound() { return ""; }
	 * 
	 * @Override protected String getHurtSound() { return ""; }
	 * 
	 * @Override protected String getDeathSound() { return ""; }
	 */
	
	@Override
	public void onUpdate() {
		squishFactor += (squishAmount - squishFactor) * 0.5F;
		prevSquishFactor = squishFactor;
		boolean flag = onGround;
		super.onUpdate();
		if (onGround && !flag) {
			squishAmount = -0.5F;
			for (int j = 0; j < 8; ++j) {
				float f = rand.nextFloat() * (float) Math.PI * 2.0F;
				float f1 = rand.nextFloat() * 0.5F + 0.5F;
				float f2 = MathHelper.sin(f) * 0.5F * f1;
				float f3 = MathHelper.cos(f) * 0.5F * f1;
				worldObj.spawnParticle("cloud", posX + (double) f2, boundingBox.minY, posZ + (double) f3, 0.0D, 0.0D, 0.0D);
			}
		} else if (!onGround && flag)
			squishAmount = 1.0F;

		alterSquishAmount();
		
		if (getAttackTarget() != null) {
			float distance = (float) getDistance(getAttackTarget().posX, getAttackTarget().boundingBox.minY, getAttackTarget().posZ);
			if (getRangeAttackTimer() < 120 && distance > 3)
				setRangeAttackTimer(getRangeAttackTimer() + 2);
			if (getRangeAttackTimer() >= 120 && distance > 3 && onGround)
				shootSporeBall(getAttackTarget(), distance);
			if (getRangeAttackTimer() == 0)
				;
		}
	}

	protected void alterSquishAmount() {
		squishAmount *= 0.8F;
	}

	@Override
	protected void updateEntityActionState() {
		despawnEntity();
		EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);

		if (entityplayer != null) {
			faceEntity(entityplayer, 10.0F, 20.0F);
			setAttackTarget(entityplayer);	
		}

		if (onGround && shroomJumpDelay-- <= 0) {
			shroomJumpDelay = getJumpDelay();
			if (entityplayer != null)
				shroomJumpDelay /= 3;
			isJumping = true;
			moveStrafing = 1.0F - rand.nextFloat() * 2.0F;
			moveForward = 1;
		} else {
			isJumping = false;
			if (onGround)
				moveStrafing = moveForward = 0.0F;
		}
	}

	protected int getJumpDelay() {
		return rand.nextInt(20) + 10;
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		super.onCollideWithPlayer(player);
		float knockback = 1;
		if (!worldObj.isRemote && player.boundingBox.maxY >= boundingBox.minY && player.boundingBox.minY <= boundingBox.maxY)
			if (worldObj.difficultySetting.ordinal() > 1)
				if (worldObj.difficultySetting == EnumDifficulty.NORMAL)
					knockback = 2;
				else if (worldObj.difficultySetting == EnumDifficulty.HARD)
					knockback = 3;
		player.addVelocity(-MathHelper.sin(rotationYaw * 3.141593F / 180.0F) * knockback, 0.4D, MathHelper.cos(rotationYaw * 3.141593F / 180.0F) * knockback);

	}

	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if (par2 > 0.0F && par2 < 2.0F) {
			attackEntityAsMob(par1Entity);
		}
	}
	
	protected void shootSporeBall(Entity entity, float distance) {
		if (distance < 16.0F)
			if (entity instanceof EntityPlayer) {
				setRangeAttackTimer(0);
				EntitySporeBall sporeBall = new EntitySporeBall(worldObj, this);
				sporeBall.posY = posY + height + 0.3D;
				worldObj.spawnEntityInWorld(sporeBall);
			}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		super.attackEntityAsMob(par1Entity);
		return true;
	}
	
	public void setRangeAttackTimer(int size) {
		dataWatcher.updateObject(20, Integer.valueOf(size));
	}

	public int getRangeAttackTimer() {
		return dataWatcher.getWatchableObjectInt(20);
	}

}