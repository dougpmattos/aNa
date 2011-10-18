/********************************************************************************
 * This file is part of the api for NCL authoring - aNa.
 *
 * Copyright (c) 2011, MídiaCom Lab (www.midiacom.uff.br)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement:
 *        This product includes the Api for NCL Authoring - aNa
 *        (http://joeldossantos.github.com/aNa).
 *
 *  * Neither the name of the lab nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without specific
 *    prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY MÍDIACOM LAB AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE MÍDIACOM LAB OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *******************************************************************************/
package br.uff.midiacom.ana.datatype.ncl.descriptor.param;

import br.uff.midiacom.ana.datatype.enums.NCLAttributes;
import br.uff.midiacom.ana.datatype.ncl.NCLElement;
import br.uff.midiacom.ana.datatype.ncl.NCLIdentifiableElement;
import br.uff.midiacom.xml.XMLElementPrototype;


public abstract class NCLDescriptorParamPrototype<T extends NCLDescriptorParamPrototype, P extends NCLElement, I extends NCLDescriptorParamImpl, Ep extends NCLDescriptorParam, V>
        extends XMLElementPrototype<T, P, I> implements NCLDescriptorParam<T, P, V> {

    protected NCLAttributes name;
    protected V value;


    /**
     * Construtor do elemento <i>descriptorParam</i> da <i>Nested Context Language</i> (NCL).
     */
    public NCLDescriptorParamPrototype() {
        impl = (I) new NCLDescriptorParamImpl<NCLIdentifiableElement, P, Ep, T, V>((T) this);
    }


    /**
     * Atribui um nome ao parâmetro. Segue os nomes padronizados de atributos do descritor.
     *
     * @param name
     *          Elemento representando o nome do parâmetro.
     */
    public void setName(NCLAttributes name) {
        impl.setName(name);
    }


    /**
     * Retorna o nome do parâmetro.
     *
     * @return
     *          elemento representando o nome do parâmetro.
     */
    public NCLAttributes getName() {
        return impl.getName();
    }


    /**
     * Atribui um valor ao parâmetro.
     *
     * @param value
     *          valor do parâmetro.
     * @throws IllegalArgumentException
     *          se o valor não estiver de acordo com o esperado.
     */
    public void setValue(V value) {
        impl.setValue(value);
    }


    /**
     * Retorna o valor do parâmetro.
     *
     * @return
     *          valor do parâmetro.
     */
    public V getValue() {
        return (V) impl.getValue();
    }


    public String parse(int ident) {
        return impl.parse(ident);
    }


    public boolean compare(T other) {
        return impl.compare(other);
    }


    /**
     * Recebe o valor do parâmetro como uma String. Este método deve ser estendido
     * de forma a atribuir o valor do tipo correto para cada parâmetro de
     * descritor.
     *
     * @param value
     *          String representando o valor do parâmetro de descritor.
     */
    protected abstract void setParamValue(String value);


    /**
     * Retorna o valor do parâmetro como uma String.
     *
     * @return
     *          String representando o valor do parâmetro do descritor.
     */
    protected abstract String getParamValue();
}
