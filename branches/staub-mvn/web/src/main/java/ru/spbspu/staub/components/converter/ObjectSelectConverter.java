package ru.spbspu.staub.components.converter;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import java.util.*;

/**
 * Custom JSF Converter for converting values in h:selectOneListbox
 * directly into objects.
 * Idea ported from Apache Trinidad.
 * TODO may be refactor and simplification
 *
 * @author Konstantin Grigoriev
 */
public class ObjectSelectConverter implements Converter {

    /**
     * {@inheritDoc}
     */
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String submittedValue) {
        List<SelectItem> selectItems = getSelectItems(uiComponent);
        int index = getIndex(submittedValue, selectItems);
        if (index < 0) {
            return null;
        }

        SelectItem item = selectItems.get(index);
        if (item != null) {
            return item.getValue();
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        List<SelectItem> selectItems = getSelectItems(uiComponent);

        int selectedIndex = findIndex(selectItems, object);

        return String.valueOf(selectedIndex);
    }

    public List<SelectItem> getSelectItems(UIComponent component) {

        int childCount = component.getChildCount();
        if (childCount == 0) {
            return Collections.emptyList();
        }

        if (!(component instanceof ValueHolder)) {
            return Collections.emptyList();
        }
        List<SelectItem> items = null;
        for (UIComponent child : (List<UIComponent>) component.getChildren()) {
            // f:selectItem
            if (child instanceof UISelectItem) {
                if (items == null) {
                    items = new ArrayList<SelectItem>(childCount);
                }
                addSelectItem((UISelectItem) child, items);
            }
            // f:selectItems
            else if (child instanceof UISelectItems) {
                if (items == null) {
                    items = new ArrayList<SelectItem>(childCount);
                }
                addSelectItems((UISelectItems) child, items);
            }
        }

        if (items == null) {
            return Collections.emptyList();
        }

        return items;
    }

    public void addSelectItem(UISelectItem uiItem, List<SelectItem> items) {
        if (!uiItem.isRendered()) {
            items.add(null);
            return;
        }
        Object value = uiItem.getValue();
        SelectItem item;

        if (value instanceof SelectItem) {
            item = (SelectItem) value;
        } else {
            Object itemValue = uiItem.getItemValue();
            String itemLabel = uiItem.getItemLabel();
            // JSF throws a null pointer exception for null values and labels,
            // which is a serious problem at design-time.
            item = new SelectItem(itemValue == null ? "" : itemValue,
                    itemLabel == null ? "" : itemLabel,
                    uiItem.getItemDescription(),
                    uiItem.isItemDisabled());
        }

        items.add(item);
    }

    public void addSelectItems(UISelectItems uiItems, List<SelectItem> items) {
        if (!uiItems.isRendered()) {
            items.add(null);
            return;
        }

        Object value = uiItems.getValue();
        if (value instanceof SelectItem) {
            items.add((SelectItem) value);
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            for (Object anArray : array) {
                items.add((SelectItem) anArray);
            }
        } else if (value instanceof Collection) {
            for (SelectItem selectItem : ((Collection<SelectItem>) value)) {
                items.add(selectItem);
            }
        } else if (value instanceof Map) {
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
                Object label = entry.getKey();
                SelectItem item =
                        new SelectItem(entry.getValue(),
                                label == null ? (String) null : label.toString());

                items.add(item);
            }
        }
    }

    int getIndex(Object submittedValue, List<SelectItem> selectItems) {
        if ("".equals(submittedValue)) {
            return -1;
        }
        try {
            int index = Integer.parseInt(submittedValue.toString());

            if ((-1 < index) && (selectItems.size() > index)) {
                return index;
            } else {
                throw new IndexOutOfBoundsException("SelectOne submittedValue''s index " + index + " is out of bounds. It should be between 0 and " + (selectItems.size() - 1));
            }
        }
        catch (NumberFormatException ne) {
            throw new NumberFormatException("SelectOne could not convert submittedValue''s index " + submittedValue + " into int " + ne);
        }
    }

    private int findIndex(List<SelectItem> selectItems, Object value) {
        int size = selectItems.size();
        for (int i = 0; i < size; i++) {
            SelectItem item = selectItems.get(i);
            if (item == null)
                continue;

            if (value == null) {
                Object itemValue = item.getValue();
                // =-=AEW Treat the empty string as if it were null
                if ((itemValue == null) || "".equals(itemValue))
                    return i;
            } else {
                if (value.equals(item.getValue()) || (value.getClass().isEnum() && item.getValue() != null && value.toString().equals(item.getValue().toString())))
                    return i;
            }
        }
        return -1;
    }
}
