package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class GT_Cover_DoesWork extends GT_CoverBehavior {
	
	public GT_Cover_DoesWork(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aTileEntity instanceof IMachineProgress) {
			int tScale = ((IMachineProgress)aTileEntity).getMaxProgress()/15;
			if (tScale > 0 && ((IMachineProgress)aTileEntity).hasThingsToDo()) {
				aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)(((IMachineProgress)aTileEntity).getProgress()/tScale):(byte)(15-((IMachineProgress)aTileEntity).getProgress()/tScale));
			} else {
				aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)15:0);
			}
		} else {
			aTileEntity.setOutputRedstoneSignal(aSide, (byte)0);
		}
		return aCoverVariable;
	}
	
	@Override
	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer) {
		if (aPlayer instanceof EntityPlayerMP) {
			if (aCoverVariable==0)
				((EntityPlayerMP)aPlayer).sendChatToPlayer("Inverted");
			else
				((EntityPlayerMP)aPlayer).sendChatToPlayer("Normal");
		}
		return aCoverVariable==0?1:0;
	}
	
	@Override
	public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
}
