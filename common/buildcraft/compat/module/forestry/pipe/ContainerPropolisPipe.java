package buildcraft.compat.module.forestry.pipe;

import net.minecraft.entity.player.EntityPlayer;

import forestry.sorting.gui.ContainerGeneticFilter;

public class ContainerPropolisPipe extends ContainerGeneticFilter {

    public final PipeBehaviourPropolis pipeBehaviour;

    public ContainerPropolisPipe(PipeBehaviourPropolis behaviour, EntityPlayer player) {
        super(behaviour, player.inventory);
        this.pipeBehaviour = behaviour;
        behaviour.pipe.getHolder().onPlayerOpen(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        pipeBehaviour.pipe.getHolder().onPlayerClose(player);
    }
}
