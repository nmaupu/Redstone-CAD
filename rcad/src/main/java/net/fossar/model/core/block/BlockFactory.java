package net.fossar.model.core.block;

public abstract class BlockFactory {
	public static AbstractBlock getInstance(BlockType type) {
		return type.createBlock();
	}
}
