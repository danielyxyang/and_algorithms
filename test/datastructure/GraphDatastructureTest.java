package datastructure;

import static org.junit.Assert.*;

import org.junit.Test;

public class GraphDatastructureTest {

	@Test
	public void testUnionFind() {
		UnionFind uf = new UnionFind(10);
		assertEquals(uf.size(), 10);
		for(int i = 0; i < uf.size(); i++) assertEquals(i, uf.find(i));

		uf.union(1,2);
		uf.union(1,3);
		uf.union(4,5);
		uf.union(4,6);
		uf.union(7,8);
		uf.union(7,9);
		uf.union(1,4);
		
		assertEquals(0, uf.find(0));
		int component1 = uf.find(1);
		assertEquals(component1, uf.find(2));
		assertEquals(component1, uf.find(3));
		assertEquals(component1, uf.find(4));
		assertEquals(component1, uf.find(5));
		assertEquals(component1, uf.find(6));
		int component2 = uf.find(7);
		assertEquals(component2, uf.find(8));
		assertEquals(component2, uf.find(9));

		assertEquals(3, uf.size());
	}

}
