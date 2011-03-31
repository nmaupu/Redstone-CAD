package net.fossar.model.core.block;

public enum BlockType {
	AIR {
		@Override
		AbstractBlock createBlock() {
			return new Air();
		}
	},
	LEVER {
		@Override
		AbstractBlock createBlock() {
			return new Lever();
		}
	},
	TORCH {
		@Override
		AbstractBlock createBlock() {
			return new Torch();
		}
	},
	WIRE {
		@Override
		AbstractBlock createBlock() {
			return new Wire();
		}
	},
	BLOCK {
		@Override
		AbstractBlock createBlock() {
			return new Block();
		}
	};
	
	abstract AbstractBlock createBlock();
	
	public static BlockType getBlockType(AbstractBlock block) {
		if (block instanceof Lever)
			return BlockType.LEVER;
		else if (block instanceof Torch)
			return BlockType.TORCH;
		else if (block instanceof Wire)
			return BlockType.WIRE;
		else if (block instanceof Block)
			return BlockType.BLOCK;
		
		return BlockType.AIR;
	}
}
