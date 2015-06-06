package mods.immibis.core.api;

@Deprecated
public interface IRecipeSaveHandler<StateType, DiffType> {
	@Deprecated
	public StateType save();
	@Deprecated
	public void apply(DiffType diff);
	@Deprecated
	public DiffType diff(StateType from, StateType to);
}
