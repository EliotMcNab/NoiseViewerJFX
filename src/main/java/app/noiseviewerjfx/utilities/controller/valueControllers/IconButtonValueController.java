package app.noiseviewerjfx.utilities.controller.valueControllers;

import app.noiseviewerjfx.utilities.controller.valueControllers.associative.Associable;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;


public class IconButtonValueController extends ButtonValueController implements Associable {

    private final FontIcon ICON;
    private final String BUTTON_SELECTED_ICON_LITERAL;
    private final String BUTTON_DESELECTED_ICON_LITERAL;
    private final boolean TOGGLEABLE;
    private boolean isSelected = false;

    public IconButtonValueController(
            Button button,
            FontIcon icon,
            String buttonSelectedIcon,
            String buttonDeselectedIcon
    ) {
        this(button, icon, buttonSelectedIcon, buttonDeselectedIcon, false);
    }

    public IconButtonValueController(
            Button button,
            FontIcon icon,
            String buttonSelectedIcon,
            String buttonDeselectedIcon,
            boolean toggleable
    ) {
        super(button);

        this.ICON = icon;
        this.BUTTON_SELECTED_ICON_LITERAL = buttonSelectedIcon;
        this.BUTTON_DESELECTED_ICON_LITERAL = buttonDeselectedIcon;
        this.TOGGLEABLE = toggleable;
    }

    @Override
    protected void onButtonPress() {
        toggleButton();
    }

    @Override
    protected void onButtonRelease() {
        if (TOGGLEABLE) toggleButton();
    }

    private void toggleButton() {
        isSelected = !isSelected;
        updateIcon();
        newState();
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
