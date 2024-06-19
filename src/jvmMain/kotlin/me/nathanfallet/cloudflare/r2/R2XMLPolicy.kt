package me.nathanfallet.cloudflare.r2

import nl.adaptivity.xmlutil.QName
import nl.adaptivity.xmlutil.serialization.DefaultXmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.OutputKind
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.structure.SafeParentInfo

// Inspired from https://github.com/pdvrieze/xmlutil/blob/master/examples/JACKSON.md
object R2XMLPolicy : DefaultXmlSerializationPolicy(
    false,
    encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.NEVER,
    //unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER // WTF NoSuchMethodError
) {

    /**
     * Rather than replacing the method wholesale, just make attributes into elements unless the [XmlElement] annotation
     * is present with a `false` value on the value attribute.
     */
    override fun effectiveOutputKind(
        serializerParent: SafeParentInfo,
        tagParent: SafeParentInfo,
        canBeAttribute: Boolean,
    ): OutputKind {
        val r = super.effectiveOutputKind(serializerParent, tagParent, canBeAttribute)
        return when {
            // Do take into account the XmlElement annotation
            r == OutputKind.Attribute && serializerParent.elementUseAnnotations.firstNotNullOfOrNull { it as? XmlElement }?.value != false -> OutputKind.Element
            else -> r
        }
    }

    /**
     * Jackson naming policy is based upon use name only. However, for this policy we do take the type annotation
     * if it is available. If there is no annotation for the name, we get the name out of the useName in all cases
     * (the default policy is dependent on member kind and the output used (attribute vs element)).
     */
    override fun effectiveName(
        serializerParent: SafeParentInfo,
        tagParent: SafeParentInfo,
        outputKind: OutputKind,
        useName: XmlSerializationPolicy.DeclaredNameInfo,
    ): QName = useName.annotatedName
        ?: serializerParent.elementTypeDescriptor.typeQname
        ?: serialNameToQName(useName.serialName, tagParent.namespace)

}
