package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

public class CategoryCommandToCategoryTest {

	 public static final Long ID_VALUE = new Long(1L);
	 public static final String DESCRIPTION = "description";
	 CategoryCommandToCategory conveter;

	    
	@Before
	public void setUp() throws Exception {
		conveter = new CategoryCommandToCategory();
	}
	
	@Test
    public void testNullObject() throws Exception {
        assertNull(conveter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(conveter.convert(new CategoryCommand()));
    }


	@Test
	public void convert() {
		 //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = conveter.convert(categoryCommand);

        //then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
	}

}
