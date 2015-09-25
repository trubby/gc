package Trubby.co.th;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.EntityTypes;

public class NMSUtil {
	public void registerEntity(String name, int id, Class<PigEdit> class1, Class<PigEdit> class2) {
		try {
			List<Map<?, ?>> dataMaps = new ArrayList();
			Field[] arrayOfField1;
			int j = (arrayOfField1 = EntityTypes.class.getDeclaredFields()).length;
			for (int i = 0; i < j; i++) {
				Field f = arrayOfField1[i];
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map) f.get(null));
				}
			}
			if (((Map) dataMaps.get(2)).containsKey(Integer.valueOf(id))) {
				((Map) dataMaps.get(0)).remove(name);
				((Map) dataMaps.get(2)).remove(Integer.valueOf(id));
			}
			Method method = EntityTypes.class.getDeclaredMethod("a",
					new Class[] { Class.class, String.class, Integer.TYPE });
			method.setAccessible(true);
			method.invoke(null, new Object[] { class2, name, Integer.valueOf(id) });
			Field[] arrayOfField2;
			int k = (arrayOfField2 = BiomeBase.class.getDeclaredFields()).length;
			for (j = 0; j < k; j++) {
				Field f = arrayOfField2[j];
				if ((f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName())) && (f.get(null) != null)) {
					Field[] arrayOfField3;
					int n = (arrayOfField3 = BiomeBase.class.getDeclaredFields()).length;
					for (int m = 0; m < n; m++) {
						Field list = arrayOfField3[m];
						if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
							list.setAccessible(true);

							List<BiomeBase.BiomeMeta> metaList = (List) list.get(f.get(null));
							for (BiomeBase.BiomeMeta meta : metaList) {
								Field clazz = BiomeBase.BiomeMeta.class.getDeclaredFields()[0];
								if (clazz.get(meta).equals(class1)) {
									clazz.set(meta, class2);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
