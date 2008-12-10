package org.identityconnectors.framework.spi.operations;

import java.util.Set;

import org.identityconnectors.framework.api.operations.UpdateApiOp;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.OperationalAttributes;
import org.identityconnectors.framework.common.objects.Uid;

/**
 * More advanced implementation of {@link UpdateOp} to be implemented by
 * connectors that wish to offer better performance and atomicity semantics
 * for the methods {@link UpdateApiOp#addAttributeValues(ObjectClass, Uid, Set, OperationOptions)}
 * and {@link UpdateApiOp#removeAttributeValues(ObjectClass, Uid, Set, OperationOptions)}.
 */
public interface UpdateAttributeValuesOp extends UpdateOp {
    
    /**
     * Update the object specified by the {@link ObjectClass} and {@link Uid}, 
     * adding to the current values of each attribute the values provided.
     * <p>
     * For each attribute that the input set contains, add to
     * the current values of that attribute in the target object all of the
     * values of that attribute in the input set.
     * <p>
     * NOTE that this does not specify how to handle duplicate values. 
     * The general assumption for an attribute of a {@code ConnectorObject} 
     * is that the values for an attribute may contain duplicates. 
     * Therefore, in general simply <em>append</em> the provided values 
     * to the current value for each attribute.
     * <p>
     * @param objclass
     *            the type of object to modify. Will never be null.
     * @param uid
     *            the uid of the object to modify. Will never be null.
     * @param valuesToAdd
     *            set of {@link Attribute} deltas. The values for the attributes
     *            in this set represent the values to add to attributes in the object.
     *            merged. This set will never include {@link OperationalAttributes operational attributes}. 
     *            Will never be null.
     * @param options
     *            additional options that impact the way this operation is run.
     *            Will never be null.
     * @return the {@link Uid} of the updated object in case the update changes
     *         the formation of the unique identifier.
     */
    public Uid addAttributeValues(ObjectClass objclass,
            Uid uid,
            Set<Attribute> valuesToAdd,
            OperationOptions options);
    
    /**
     * Update the object specified by the {@link ObjectClass} and {@link Uid}, 
     * removing from the current values of each attribute the values provided.
     * <p>
     * For each attribute that the input set contains, 
     * remove from the current values of that attribute in the target object 
     * any value that matches one of the values of the attribute from the input set.
     * <p>
     * NOTE that this does not specify how to handle unmatched values. 
     * The general assumption for an attribute of a {@code ConnectorObject}
     * is that the values for an attribute are merely <i>representational state</i>.
     * Therefore, the implementer should simply ignore any provided value
     * that does not match a current value of that attribute in the target
     * object. Deleting an unmatched value should always succeed.
     * @param objclass
     *            the type of object to modify. Will never be null.
     * @param uid
     *            the uid of the object to modify. Will never be null.
     * @param valuesToRemove
     *            set of {@link Attribute} deltas. The values for the attributes
     *            in this set represent the values to remove from attributes in the object.
     *            merged. This set will never include {@link OperationalAttributes operational attributes}. 
     *            Will never be null.
     * @param options
     *            additional options that impact the way this operation is run.
     *            Will never be null..
     * @return the {@link Uid} of the updated object in case the update changes
     *         the formation of the unique identifier.
     */
    public Uid removeAttributeValues(ObjectClass objclass,
            Uid uid,
            Set<Attribute> valuesToRemove,
            OperationOptions options);

}