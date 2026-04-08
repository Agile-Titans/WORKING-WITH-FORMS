import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRegistrationFormTest {

    private final EmployeeRegistrationForm form = new EmployeeRegistrationForm();

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
        JComboBox<String> box = form.createDepartmentBox();

        assertAll(
                () -> assertEquals(4, box.getItemCount()),
                () -> assertEquals("IT", box.getItemAt(0)),
                () -> assertNotEquals("Legal", box.getItemAt(1))
        );
    }

    @Test
    void testTreeStructure() {
        JScrollPane treeScroll = form.createTree();
        JTree tree = (JTree) treeScroll.getViewport().getView();

        assertNotNull(tree);
        assertEquals("Company", tree.getModel().getRoot().toString());
    }
}