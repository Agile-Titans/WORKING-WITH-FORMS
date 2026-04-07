import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRegistrationFormTest {

    private EmployeeRegistrationForm form;

    @BeforeEach
    void setUp() {
        form = new EmployeeRegistrationForm();
        form.setVisible(false);
    }


    @AfterEach
    void tearDown() {
        form.dispose();
    }

    @Test
    void testFrameProperties() {
        assertAll(
                () -> assertEquals("Employee Registration System", form.getTitle()),
                () -> assertEquals(500, form.getWidth()),
                () -> assertEquals(600, form.getHeight()),
                () -> assertNotNull(form.getLayout())
        );
    }

    @Test
    void testValidateInputFailsWhenEmpty() {
        boolean result = form.validateInput("", "", "", null);

        assertFalse(result, "Validation should fail for empty inputs");
    }

    @Test
    void testBuildSummaryMasksPassword() {
        String summary = form.buildSummary(
                "mohamed",
                "mohamed@email.com",
                "1234",
                "IT",
                "Development"
        );

        assertAll(
                () -> assertNotEquals("1234", summary),
                () -> assertTrue(summary.contains("****")),
                () -> assertTrue(summary.contains("mohamed")),
                () -> assertTrue(summary.contains("IT"))
        );
    }

    @Test
    void testDepartmentOptions() {
        JComboBox<String> box = form.departmentBox;

        assertAll(
                () -> assertEquals(4, box.getItemCount()),
                () -> assertSame("IT", box.getItemAt(0)),
                () -> assertNotSame("Legal", box.getItemAt(1))
        );
    }

    @Test
    void testTreeStructure() {
        assertNotNull(form.orgTree);
        assertEquals("Company", form.orgTree.getModel().getRoot().toString());
    }

}