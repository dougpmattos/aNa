package br.uff.midiacom.ana.node;

import br.uff.midiacom.ana.NCLBody;
import br.uff.midiacom.ana.NCLDoc;
import br.uff.midiacom.ana.NCLElement;
import br.uff.midiacom.ana.descriptor.NCLLayoutDescriptor;
import br.uff.midiacom.ana.interfaces.NCLArea;
import br.uff.midiacom.ana.interfaces.NCLInterface;
import br.uff.midiacom.ana.interfaces.NCLProperty;
import br.uff.midiacom.ana.util.ElementList;
import br.uff.midiacom.ana.util.enums.*;
import br.uff.midiacom.ana.util.exception.NCLParsingException;
import br.uff.midiacom.ana.util.exception.NCLRemovalException;
import br.uff.midiacom.ana.util.exception.XMLException;
import br.uff.midiacom.ana.util.ncl.NCLIdentifiableElementPrototype;
import br.uff.midiacom.ana.util.reference.ExternalReferenceType;
import br.uff.midiacom.ana.util.reference.PostReferenceElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class NCLEffect <T extends NCLElement,
        Ea extends NCLArea,
        Ep extends NCLProperty,
        El extends NCLLayoutDescriptor,
        En extends NCLNode,
        Ei extends NCLInterface,
        R extends ExternalReferenceType>
        extends NCLIdentifiableElementPrototype<T>
        implements NCLNode<T, En, Ei>, PostReferenceElement {

    protected NCLEffectType type;
    protected Object descriptor;
    protected Object refer;
    protected NCLInstanceType instance;
    protected ElementList<Ea> areas;
    protected ElementList<Ep> properties;

    protected ArrayList<T> references;

    public NCLEffect() {
        super();
        areas = new ElementList<Ea>();
        properties = new ElementList<Ep>();
        references = new ArrayList<T>();
    }

    public NCLEffect(String id) throws XMLException {
        super();
        areas = new ElementList<Ea>();
        properties = new ElementList<Ep>();
        references = new ArrayList<T>();
        setId(id);
    }

    @Override
    @Deprecated
    public void setDoc(T doc) {
        super.setDoc(doc);
        for (Ea aux : areas) {
            aux.setDoc(doc);
        }
        for (Ep aux : properties) {
            aux.setDoc(doc);
        }
    }

    @Override
    public void setId(String id) throws XMLException {
        if(id == null)
            throw new XMLException("Null id string");

        super.setId(id);
    }

    public void setType(NCLEffectType type) throws XMLException {
        NCLEffectType aux = this.type;
        this.type = type;
        notifyAltered(NCLElementAttributes.TYPE, aux, type);
    }

    public NCLEffectType getType() {
        return type;
    }

    public void setDescriptor(Object descriptor) throws XMLException {
        Object aux = this.descriptor;

        if(descriptor instanceof NCLLayoutDescriptor)
            ((El) descriptor).addReference(this);
        else if(descriptor instanceof ExternalReferenceType){
            ((R) descriptor).getTarget().addReference(this);
            ((R) descriptor).getAlias().addReference(this);
        }

        this.descriptor = descriptor;
        notifyAltered(NCLElementAttributes.DESCRIPTOR, aux, descriptor);

        if(aux != null){
            if(aux instanceof NCLLayoutDescriptor)
                ((El) aux).removeReference(this);
            else{
                ((R) aux).getTarget().removeReference(this);
                ((R) aux).getAlias().removeReference(this);
            }
        }
    }

    public Object getDescriptor() {
        return descriptor;
    }

    public void setRefer(Object refer) throws XMLException {
        Object aux = this.refer;

        if(refer instanceof NCLMedia)
            ((En) refer).addReference(this);
        else if(refer instanceof ExternalReferenceType){
            ((R) refer).getTarget().addReference(this);
            ((R) refer).getAlias().addReference(this);
        }

        this.refer = refer;
        notifyAltered(NCLElementAttributes.REFER, aux, refer);

        if(aux != null){
            if(aux instanceof NCLMedia)
                ((En) aux).removeReference(this);
            else{
                ((R) aux).getTarget().removeReference(this);
                ((R) aux).getAlias().removeReference(this);
            }
        }
    }

    public Object getRefer() {
        return refer;
    }

    public void setInstance(NCLInstanceType instance) throws XMLException {
        NCLInstanceType aux = this.instance;
        this.instance = instance;
        notifyAltered(NCLElementAttributes.INSTANCE, aux, instance);
    }

    public NCLInstanceType getInstance() {
        return instance;
    }

    public boolean addArea(Ea area) throws XMLException {
        if(areas.add(area)){
            notifyInserted((T) area);
            area.setParent(this);
            return true;
        }
        return false;
    }


    /**
     * Removes an anchor of the effect. An anchor represents a subpart of the node
     * content. The effect can have none or several area elements.
     *
     * @param area
     *          element representing an anchor.
     * @return
     *          true if the area was removed.
     * @throws XMLException
     *          if the element representing the area is null.
     */
    public boolean removeArea(Ea area) throws XMLException {
        if(!area.getReferences().isEmpty())
            throw new NCLRemovalException("This element has a reference to it."
                    + " The reference must be undone before erasing this element.");

        if(areas.remove(area)){
            notifyRemoved((T) area);
            return true;
        }
        return false;
    }


    /**
     * Removes an anchor of the effect. An anchor represents a subpart of the node
     * content. The effect can have none or several area elements.
     *
     * @param id
     *          string representing the id of the element representing an anchor.
     * @return
     *          true if the area was removed.
     * @throws XMLException
     *          if the string is null or empty.
     */
    public boolean removeArea(String id) throws XMLException {
        Ea aux = areas.get(id);
        return removeArea(aux);
    }


    /**
     * Verifies if the effect has a specific element representing an anchor.
     * An anchor represents a subpart of the node content. The effect can have
     * none or several area elements.
     *
     * @param area
     *          element representing an anchor.
     * @return
     *          true if the effect has the area element.
     * @throws XMLException
     *          if the element representing the are is null.
     */
    public boolean hasArea(Ea area) throws XMLException {
        return areas.contains(area);
    }


    /**
     * Verifies if the effect has an anchor with a specific id. An anchor
     * represents a subpart of the node content. The effect can have none or
     * several area elements.
     *
     * @param id
     *          string representing the id of the element representing an anchor.
     * @return
     *          true if the effect has the area element.
     * @throws XMLException
     *          if the string is null or empty.
     */
    public boolean hasArea(String id) throws XMLException {
        return areas.get(id) != null;
    }


    /**
     * Verifies if the effect has at least one anchor. An anchor represents a
     * subpart of the node content. The effect can have none or several area
     * elements.
     *
     * @return
     *          true if the effect has at least one area.
     */
    public boolean hasArea() {
        return !areas.isEmpty();
    }


    /**
     * Returns the list of anchors that a media have. An anchor represents a
     * subpart of the node content. The effect can have none or several area
     * elements.
     *
     * @return
     *          element list with all anchors.
     */
    public ElementList<Ea> getAreas() {
        return areas;
    }


    /**
     * Returns the anchor with a specific id. An anchor represents a
     * subpart of the node content. The effect can have none or several area
     * elements.
     *
     * @param id
     *          string representing the id of the element representing an anchor.
     * @return
     *          element representing a composite node interface point.
     */
    public Ea getArea(String id) throws XMLException {
        return areas.get(id);
    }


    /**
     * Adds a property to the effect. A property represents a node attribute. The
     * media can have none or several property elements.
     *
     * @param property
     *          element representing a property.
     * @return
     *          true if the property was added.
     * @throws XMLException
     *          if the element representing the property is null.
     */
    public boolean addProperty(Ep property) throws XMLException {
        if(properties.add(property)){
            notifyInserted((T) property);
            property.setParent(this);
            return true;
        }
        return false;
    }


    /**
     * Removes a property of the effect. A property represents a node attribute.
     * The media can have none or several property elements.
     *
     * @param property
     *          element representing a property.
     * @return
     *          true if the property was removed.
     * @throws XMLException
     *          if the element representing the property is null.
     */
    public boolean removeProperty(Ep property) throws XMLException {
        if(!property.getReferences().isEmpty())
            throw new NCLRemovalException("This element has a reference to it."
                    + " The reference must be undone before erasing this element.");

        if(properties.remove(property)){
            notifyRemoved((T) property);
            return true;
        }
        return false;
    }


    /**
     * Removes a property of the effect. A property represents a node attribute.
     * The media can have none or several property elements.
     *
     * @param name
     *          string representing the id of the element representing a property.
     * @return
     *          true if the property was removed.
     * @throws XMLException
     *          if the string is null or empty.
     */
    public boolean removeProperty(String name) throws XMLException {
        Ep aux = properties.get(name);
        return removeProperty(aux);
    }


    /**
     * Verifies if the effect has a specific element representing a property.
     * A property represents a node attribute. The effect can have none or several
     * property elements.
     *
     * @param property
     *          element representing a property.
     * @return
     *          true if the effect has the property element.
     * @throws XMLException
     *          if the element representing the property is null.
     */
    public boolean hasProperty(Ep property) throws XMLException {
        return properties.contains(property);
    }


    /**
     * Verifies if the effect has a property with a specific id. A property
     * represents a node attribute. The effect can have none or several property
     * elements.
     *
     * @param name
     *          string representing the id of the element representing a property.
     * @return
     *          true if the effect has the property element.
     * @throws XMLException
     *          if the string is null or empty.
     */
    public boolean hasProperty(String name) throws XMLException {
        return properties.get(name) != null;
    }


    /**
     * Verifies if the media has at least one property. A property represents a
     * node attribute. The effect can have none or several property elements.
     *
     * @return
     *          true if the effect has at least one property.
     */
    public boolean hasProperty() {
        return !properties.isEmpty();
    }


    /**
     * Returns the list of properties that a media have. A property represents a
     * node attribute. The effect can have none or several property elements.
     *
     * @return
     *          element list with all properties.
     */
    public ElementList<Ep> getProperties() {
        return properties;
    }


    /**
     * Returns the property with a specific name. A property represents a
     * node attribute. The effect can have none or several property elements.
     *
     * @param name
     *          string representing the name of the property.
     * @return
     *          element representing a property.
     */
    public Ep getProperty(String name) throws XMLException {
        return properties.get(name);
    }

    /**
     * Returns the effect type according to its content as defined in the
     * attribute src.
     * @return
     *          effect type from the enumeration <i>NCLMediaType</i>.
     */
    public NCLEffectType getEffectType() {
        if(getType() != null)
            return getType();

        return NCLEffectType.OTHER;
    }

    @Override
    public boolean compare(T other) {
        if(other == null || !(other instanceof NCLEffect))
            return false;

        boolean result = true;

        Object aux;
        if((aux = getId()) != null)
            result &= aux.equals(((NCLEffect) other).getId());
        if((aux = getType()) != null)
            result &= aux.equals(((NCLEffect) other).getType());
        if((aux = getInstance()) != null)
            result &= aux.equals(((NCLEffect) other).getInstance());

        Object ref = getDescriptor();
        Object oref = ((NCLEffect) other).getDescriptor();
        if(ref != null && oref != null){
            if(ref instanceof NCLLayoutDescriptor && oref instanceof NCLLayoutDescriptor)
                result &= ((El) ref).compare((El) oref);
            else if(ref instanceof ExternalReferenceType && oref instanceof ExternalReferenceType)
                result &= ((R) ref).equals((R) oref);
            else
                result = false;
        }

        ref = getRefer();
        oref = ((NCLEffect) other).getRefer();
        if(ref != null && oref != null){
            if(ref instanceof NCLEffect && oref instanceof NCLEffect)
                result &= ((En) ref).compare((En) oref);
            else if(ref instanceof ExternalReferenceType && oref instanceof ExternalReferenceType)
                result &= ((R) ref).equals((R) oref);
            else
                result = false;
        }

        ElementList<Ea> otherare = ((NCLEffect) other).getAreas();
        result &= areas.size() == otherare.size();
        for (Ea are : areas) {
            try {
                result &= otherare.contains(are);
            } catch (XMLException ex) {}
            if(!result)
                break;
        }

        ElementList<Ep> otherpro = ((NCLEffect) other).getProperties();
        result &= properties.size() == otherpro.size();
        for (Ep pro : properties) {
            try {
                result &= otherpro.contains(pro);
            } catch (XMLException ex) {}
            if(!result)
                break;
        }

        return result;
    }

    @Override
    public String parse(int ident) {
        String space, content;

        if(ident < 0)
            ident = 0;

        // Element indentation
        space = "";
        for(int i = 0; i < ident; i++)
            space += "\t";


        // <effect> element and attributes declaration
        content = space + "<effect";
        content += parseAttributes();

        // Test if the effect has content
        if(hasArea() || hasProperty()){
            content += ">\n";

            content += parseElements(ident + 1);

            content += space + "</effect>\n";
        }
        else
            content += "/>\n";

        return content;
    }

    @Override
    public void load(Element element) throws NCLParsingException {
        NodeList nl;

        try{
            loadId(element);
            loadType(element);
            loadDescriptor(element);
            loadInstance(element);
        }
        catch(XMLException ex){
            String aux = getId();
            if(aux != null)
                aux = "(" + aux + ")";
            else
                aux = "";

            throw new NCLParsingException("Effect" + aux + ":\n" + ex.getMessage());
        }

        try{
            // create the child nodes
            nl = element.getChildNodes();
            for(int i=0; i < nl.getLength(); i++){
                Node nd = nl.item(i);
                if(nd instanceof Element){
                    Element el = (Element) nl.item(i);

                    loadAreas(el);
                    loadProperties(el);
                }
            }
        }
        catch(XMLException ex){
            String aux = getId();
            if(aux != null)
                aux = "(" + aux + ")";
            else
                aux = "";

            throw new NCLParsingException("Effect" + aux + " > " + ex.getMessage());
        }

        try{
            loadRefer(element);
        }
        catch(XMLException ex){
            String aux = getId();
            if(aux != null)
                aux = "(" + aux + ")";
            else
                aux = "";

            throw new NCLParsingException("Effect" + aux + ":\n" + ex.getMessage());
        }
    }

    protected String parseAttributes() {
        String content = "";

        content += parseId();
        content += parseRefer();
        content += parseInstance();
        content += parseType();
        content += parseDescriptor();

        return content;
    }


    protected String parseElements(int ident) {
        String content = "";

        content += parseAreas(ident);
        content += parseProperties(ident);

        return content;
    }


    protected String parseId() {
        String aux = getId();
        if(aux != null)
            return " id='" + aux + "'";
        else
            return "";
    }


    protected void loadId(Element element) throws XMLException {
        String att_name, att_var;

        // set the id (required)
        att_name = NCLElementAttributes.ID.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty())
            setId(att_var);
        else
            throw new NCLParsingException("Could not find " + att_name + " attribute.");
    }

    protected String parseType() {
        NCLEffectType aux = getType();
        if(aux != null)
            return " type='" + aux.toString() + "'";
        else
            return "";
    }


    protected void loadType(Element element) throws XMLException {
        String att_name, att_var;

        // set the type (optional)
        att_name = NCLElementAttributes.TYPE.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty())
            setType(NCLEffectType.getEnumType(att_var));
    }


    protected String parseDescriptor() {
        Object aux = getDescriptor();
        if(aux == null)
            return "";

        if(aux instanceof NCLLayoutDescriptor)
            return " descriptor='" + ((El) aux).getId() + "'";
        else
            return " descriptor='" + ((R) aux).toString() + "'";
    }


    protected void loadDescriptor(Element element) throws XMLException {
        String att_name, att_var;

        // set the descriptor (optional)
        att_name = NCLElementAttributes.DESCRIPTOR.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty()){
            NCLDoc d = (NCLDoc) getDoc();
            String[] des = adjustReference(att_var);
            setDescriptor(d.getHead().findDescriptor(des[0], des[1]));
        }
    }


    protected String parseRefer() {
        Object aux = getRefer();
        if(aux == null)
            return "";

        if(aux instanceof NCLMedia)
            return " refer='" + ((En) aux).getId() + "'";
        else
            return " refer='" + ((R) aux).toString() + "'";
    }


    protected void loadRefer(Element element) throws XMLException {
        String att_name, att_var;

        // set the refer (optional)
        att_name = NCLElementAttributes.REFER.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty()){
            En ref = (En) new NCLMedia(att_var);
            setRefer(ref);
            ((NCLDoc) getDoc()).waitReference(this);
        }
    }


    protected String parseInstance() {
        NCLInstanceType aux = getInstance();
        if(aux != null)
            return " instance='" + aux.toString() + "'";
        else
            return "";
    }


    protected void loadInstance(Element element) throws XMLException {
        String att_name, att_var;

        // set the instance (optional)
        att_name = NCLElementAttributes.INSTANCE.toString();
        if(!(att_var = element.getAttribute(att_name)).isEmpty())
            setInstance(NCLInstanceType.getEnumType(att_var));
    }


    protected String parseAreas(int ident) {
        if(!hasArea())
            return "";

        String content = "";
        for(Ea aux : areas)
            content += aux.parse(ident);

        return content;
    }


    protected void loadAreas(Element element) throws XMLException {
        //create the areas
        if(element.getTagName().equals(NCLElementAttributes.AREA.toString())){
            Ea inst = createArea();
            addArea(inst);
            inst.load(element);
        }
    }


    protected String parseProperties(int ident) {
        if(!hasProperty())
            return "";

        String content = "";
        for(Ep aux : properties)
            content += aux.parse(ident);

        return content;
    }


    protected void loadProperties(Element element) throws XMLException {
        // create the properties
        if(element.getTagName().equals(NCLElementAttributes.PROPERTY.toString())){
            Ep inst = createProperty();
            addProperty(inst);
            inst.load(element);
        }
    }

    @Override
    public Ei findInterface(String id) throws XMLException {
        Ei result;

        // first search in the reused media
        Object aux;
        if((aux = getRefer()) != null){
            if(aux instanceof NCLEffect)
                result = (Ei) ((En) aux).findInterface(id);
            else
                result = (Ei) ((En) ((ExternalReferenceType) aux).getTarget()).findInterface(id);

            if(result != null)
                return result;
        }

        // search as a property
        result = (Ei) properties.get(id);
        if(result != null)
            return result;

        // search as an area
        result = (Ei) areas.get(id);
        if(result != null)
            return result;

        return null;
    }


    @Override
    public En findNode(String id) {
        if(getId().equals(id))
            return (En) this;
        else
            return null;
    }

    @Override
    @Deprecated
    public void fixReference() throws NCLParsingException {
        String aux;

        try{
            // fix the refer
            if(getRefer() != null && (aux = ((En) getRefer()).getId()) != null){
                En ref = (En) ((NCLBody) ((NCLDoc) getDoc()).getBody()).findNode(aux);
                setRefer(ref);
            }
        }
        catch(XMLException ex){
            aux = getId();
            if(aux != null)
                aux = "(" + aux + ")";
            else
                aux = "";

            throw new NCLParsingException("Effect" + aux + ". Fixing reference:\n" + ex.getMessage());
        }
    }


    @Override
    @Deprecated
    public boolean addReference(T reference) throws XMLException {
        return references.add(reference);
    }


    @Override
    @Deprecated
    public boolean removeReference(T reference) throws XMLException {
        return references.remove(reference);
    }


    @Override
    public ArrayList getReferences() {
        return references;
    }

    @Override
    public void clean() throws XMLException {
        setParent(null);

        if(descriptor != null){
            if(descriptor instanceof NCLLayoutDescriptor)
                ((El)descriptor).removeReference(this);
            else if(descriptor instanceof ExternalReferenceType){
                ((R) descriptor).getTarget().removeReference(this);
                ((R) descriptor).getAlias().removeReference(this);
            }
        }

        if(refer != null){
            if(refer instanceof NCLEffect)
                ((NCLEffect)refer).removeReference(this);
            else if(refer instanceof ExternalReferenceType){
                ((R) refer).getTarget().removeReference(this);
                ((R) refer).getAlias().removeReference(this);
            }
        }

        type = null;
        descriptor = null;
        refer = null;
        instance = null;

        for(Ea a : areas)
            a.clean();

        for(Ep p : properties)
            p.clean();
    }

    /**
     * Function to create the child element <i>area</i>.
     * This function must be overwritten in classes that extends this one.
     *
     * @return
     *          element representing the child <i>area</i>.
     */
    protected Ea createArea() throws XMLException {
        return (Ea) new NCLArea();
    }


    /**
     * Function to create the child element <i>property</i>.
     * This function must be overwritten in classes that extends this one.
     *
     * @return
     *          element representing the child <i>property</i>.
     */
    protected Ep createProperty() throws XMLException {
        return (Ep) new NCLProperty();
    }

}
