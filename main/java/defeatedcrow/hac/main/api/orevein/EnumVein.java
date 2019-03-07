package defeatedcrow.hac.main.api.orevein;

public enum EnumVein {
	HIGH_SEDIMENT(0, 5),
	SEDIMENT(1, 5),
	SAND_SEDIMENT(2, 4),
	BAUXITE(3, 4),
	KIESLAGER(4, 4),
	QUARTZ(5, 5),
	UNDERLAVA(6, 3),
	GEODE(7, 4),
	GUANO(8, 4),
	SKARN(9, 8),
	SKARN_UNDER(10, 8);

	public static final EnumVein[] VALUES = {
			SEDIMENT, SAND_SEDIMENT, BAUXITE, KIESLAGER, QUARTZ, UNDERLAVA, GEODE, GUANO, SKARN, SKARN_UNDER
	};

	public final int id;
	public final int range;

	EnumVein(int i, int r) {
		id = i;
		range = r;
	}

	public static EnumVein getType(String name) {
		for (EnumVein ret : VALUES) {
			if (ret.name().equalsIgnoreCase(name)) {
				return ret;
			}
		}
		return HIGH_SEDIMENT;
	}
}
