import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import data.model.dimensions;
import data.model.represents;
import data.model.semanticRelations;
import data.model.units;


public class TestRepresent {

	@Test
	public void test() throws IOException {
		represents test1=represents.oneRepresent(2);
	}
	@Test
	public void test2() throws IOException {
		semanticRelations test2=semanticRelations.oneRepresent(2);
	}
	@Test
	public void test3() throws IOException {
		units test2=units.oneRepresent(0);
	}
	@Test
	public void test4() throws IOException {
		dimensions test3=dimensions.oneRepresent(0);
	}

}
