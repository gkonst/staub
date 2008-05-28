package ru.spbspu.staub.components.taglib;

import ru.spbspu.staub.components.html.HtmlSelectBooleanRadio;
import ru.spbspu.staub.components.renderkit.BooleanRadioRenderer;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

/**
 * Tag definition for  <code>HtmlSelectBooleanRadio<code> component.
 *
 * @author Konstantin Grigoriev
 */
public class SelectBooleanRadioTag extends UIComponentELTag {
    private ValueExpression converter;
    private ValueExpression converterMessage;
    private ValueExpression immediate;
    private ValueExpression required;
    private ValueExpression requiredMessage;
    private MethodExpression validator;
    private ValueExpression validatorMessage;
    private ValueExpression value;
    private MethodExpression valueChangeListener;
    private ValueExpression accesskey;
    private ValueExpression dir;
    private ValueExpression disabled;
    private ValueExpression label;
    private ValueExpression lang;
    private ValueExpression onblur;
    private ValueExpression onchange;
    private ValueExpression onclick;
    private ValueExpression ondblclick;
    private ValueExpression onfocus;
    private ValueExpression onkeydown;
    private ValueExpression onkeypress;
    private ValueExpression onkeyup;
    private ValueExpression onmousedown;
    private ValueExpression onmousemove;
    private ValueExpression onmouseout;
    private ValueExpression onmouseover;
    private ValueExpression onmouseup;
    private ValueExpression onselect;
    private ValueExpression readonly;
    private ValueExpression style;
    private ValueExpression styleClass;
    private ValueExpression tabindex;
    private ValueExpression title;
    private ValueExpression group;

    @Override
    public String getRendererType() {
        return BooleanRadioRenderer.RENDERER_TYPE;
    }

    @Override
    public String getComponentType() {
        return HtmlSelectBooleanRadio.COMPONENT_TYPE;
    }

    @Override
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UISelectBoolean selectboolean = null;
        try {
            selectboolean = (UISelectBoolean) component;
        }
        catch (ClassCastException cce) {
            throw new IllegalStateException((new StringBuilder()).append("Component ").append(component.toString()).append(" not expected type.  Expected: javax.faces.component.UISelectBoolean.  Perhaps you're missing a tag?").toString());
        }
        if (converter != null)
            if (!converter.isLiteralText()) {
                selectboolean.setValueExpression("converter", converter);
            } else {
                javax.faces.convert.Converter conv = FacesContext.getCurrentInstance().getApplication().createConverter(converter.getExpressionString());
                selectboolean.setConverter(conv);
            }
        if (converterMessage != null)
            selectboolean.setValueExpression("converterMessage", converterMessage);
        if (immediate != null)
            selectboolean.setValueExpression("immediate", immediate);
        if (required != null)
            selectboolean.setValueExpression("required", required);
        if (requiredMessage != null)
            selectboolean.setValueExpression("requiredMessage", requiredMessage);
        if (validator != null)
            selectboolean.addValidator(new MethodExpressionValidator(validator));
        if (validatorMessage != null)
            selectboolean.setValueExpression("validatorMessage", validatorMessage);
        if (value != null)
            selectboolean.setValueExpression("value", value);
        if (valueChangeListener != null)
            selectboolean.addValueChangeListener(new MethodExpressionValueChangeListener(valueChangeListener));
        if (accesskey != null)
            selectboolean.setValueExpression("accesskey", accesskey);
        if (dir != null)
            selectboolean.setValueExpression("dir", dir);
        if (disabled != null)
            selectboolean.setValueExpression("disabled", disabled);
        if (label != null)
            selectboolean.setValueExpression("label", label);
        if (lang != null)
            selectboolean.setValueExpression("lang", lang);
        if (onblur != null)
            selectboolean.setValueExpression("onblur", onblur);
        if (onchange != null)
            selectboolean.setValueExpression("onchange", onchange);
        if (onclick != null)
            selectboolean.setValueExpression("onclick", onclick);
        if (ondblclick != null)
            selectboolean.setValueExpression("ondblclick", ondblclick);
        if (onfocus != null)
            selectboolean.setValueExpression("onfocus", onfocus);
        if (onkeydown != null)
            selectboolean.setValueExpression("onkeydown", onkeydown);
        if (onkeypress != null)
            selectboolean.setValueExpression("onkeypress", onkeypress);
        if (onkeyup != null)
            selectboolean.setValueExpression("onkeyup", onkeyup);
        if (onmousedown != null)
            selectboolean.setValueExpression("onmousedown", onmousedown);
        if (onmousemove != null)
            selectboolean.setValueExpression("onmousemove", onmousemove);
        if (onmouseout != null)
            selectboolean.setValueExpression("onmouseout", onmouseout);
        if (onmouseover != null)
            selectboolean.setValueExpression("onmouseover", onmouseover);
        if (onmouseup != null)
            selectboolean.setValueExpression("onmouseup", onmouseup);
        if (onselect != null)
            selectboolean.setValueExpression("onselect", onselect);
        if (readonly != null)
            selectboolean.setValueExpression("readonly", readonly);
        if (style != null)
            selectboolean.setValueExpression("style", style);
        if (styleClass != null)
            selectboolean.setValueExpression("styleClass", styleClass);
        if (tabindex != null)
            selectboolean.setValueExpression("tabindex", tabindex);
        if (title != null)
            selectboolean.setValueExpression("title", title);
        if (group != null)
            selectboolean.setValueExpression("group", group);
    }

    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    public void release() {
        super.release();
        converter = null;
        converterMessage = null;
        immediate = null;
        required = null;
        requiredMessage = null;
        validator = null;
        validatorMessage = null;
        value = null;
        valueChangeListener = null;
        accesskey = null;
        dir = null;
        disabled = null;
        label = null;
        lang = null;
        onblur = null;
        onchange = null;
        onclick = null;
        ondblclick = null;
        onfocus = null;
        onkeydown = null;
        onkeypress = null;
        onkeyup = null;
        onmousedown = null;
        onmousemove = null;
        onmouseout = null;
        onmouseover = null;
        onmouseup = null;
        onselect = null;
        readonly = null;
        style = null;
        styleClass = null;
        tabindex = null;
        title = null;
        group = null;
    }

    public String getDebugString() {
        return (new StringBuilder()).append("id: ").append(getId()).append(" class: ").append(getClass().getName()).toString();
    }

    public void setConverter(ValueExpression converter) {
        this.converter = converter;
    }

    public void setConverterMessage(ValueExpression converterMessage) {
        this.converterMessage = converterMessage;
    }

    public void setImmediate(ValueExpression immediate) {
        this.immediate = immediate;
    }

    public void setRequired(ValueExpression required) {
        this.required = required;
    }

    public void setRequiredMessage(ValueExpression requiredMessage) {
        this.requiredMessage = requiredMessage;
    }

    public void setValidator(MethodExpression validator) {
        this.validator = validator;
    }

    public void setValidatorMessage(ValueExpression validatorMessage) {
        this.validatorMessage = validatorMessage;
    }

    public void setValue(ValueExpression value) {
        this.value = value;
    }

    public void setValueChangeListener(MethodExpression valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void setAccesskey(ValueExpression accesskey) {
        this.accesskey = accesskey;
    }

    public void setDir(ValueExpression dir) {
        this.dir = dir;
    }

    public void setDisabled(ValueExpression disabled) {
        this.disabled = disabled;
    }

    public void setLabel(ValueExpression label) {
        this.label = label;
    }

    public void setLang(ValueExpression lang) {
        this.lang = lang;
    }

    public void setOnblur(ValueExpression onblur) {
        this.onblur = onblur;
    }

    public void setOnchange(ValueExpression onchange) {
        this.onchange = onchange;
    }

    public void setOnclick(ValueExpression onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(ValueExpression ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnfocus(ValueExpression onfocus) {
        this.onfocus = onfocus;
    }

    public void setOnkeydown(ValueExpression onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeypress(ValueExpression onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeyup(ValueExpression onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnmousedown(ValueExpression onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmousemove(ValueExpression onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(ValueExpression onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(ValueExpression onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmouseup(ValueExpression onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setOnselect(ValueExpression onselect) {
        this.onselect = onselect;
    }

    public void setReadonly(ValueExpression readonly) {
        this.readonly = readonly;
    }

    public void setStyle(ValueExpression style) {
        this.style = style;
    }

    public void setStyleClass(ValueExpression styleClass) {
        this.styleClass = styleClass;
    }

    public void setTabindex(ValueExpression tabindex) {
        this.tabindex = tabindex;
    }

    public void setTitle(ValueExpression title) {
        this.title = title;
    }

    public ValueExpression getGroup() {
        return group;
    }

    public void setGroup(ValueExpression group) {
        this.group = group;
    }
}
