package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;


public class IconButtonValueController extends ButtonValueController implements Associable {

    private final FontIcon ICON;
    private final String BUTTON_SELECTED_ICON_LITERAL;
    private final String BUTTON_DESELECTED_ICON_LITERAL;
    private boolean isSelected = false;

    public IconButtonValueController(
            Button button,
            FontIcon icon,
            String buttonSelectedIcon,
            String buttonDeselectedIcon
    ) {
        super(button);

        this.ICON = icon;
        this.BUTTON_SELECTED_ICON_LITERAL = buttonSelectedIcon;
        this.BUTTON_DESELECTED_ICON_LITERAL = buttonDeselectedIcon;

        addListeners();
    }

    @Override
    protected void addListeners() {
        BUTTON.setOnAction(selectButton());
    }

    private EventHandler<ActionEvent> selectButton() {
        return actionEvent -> {
            isSelected = !isSelected;
            updateIcon();
            newState();
        };
    }

    private void updateIcon() {
        if (isSelected) {
            ICON.setIconLiteral(BUTTON_SELECTED_ICON_LITERAL);
        } else {
            ICON.setIconLiteral(BUTTON_DESELECTED_ICON_LITERAL);
        }
    }

    @Override
    public double getValue() {
        return isSelected ? 1 : 0;
    }
}
