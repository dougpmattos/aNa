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


public class NCLDoubleDescriptorParamPrototype<T extends NCLDoubleDescriptorParamPrototype, P extends NCLElement, I extends NCLDescriptorParamImpl, Ep extends NCLDescriptorParam>
        extends NCLDescriptorParamPrototype<T, P, I, Ep, Double> {

    private Boolean relative;


    public NCLDoubleDescriptorParamPrototype() {
        super();
    }


    @Override
    public void setName(NCLAttributes name) throws IllegalArgumentException {
        if(!name.equals(NCLAttributes.TOP) && !name.equals(NCLAttributes.LEFT)
                && !name.equals(NCLAttributes.BOTTOM) && !name.equals(NCLAttributes.RIGHT)
                && !name.equals(NCLAttributes.WIDTH) && !name.equals(NCLAttributes.HEIGHT)
                && !name.equals(NCLAttributes.FONT_SIZE))
            throw new IllegalArgumentException("This parameter type can not be used with this name.");

        super.setName(name);
    }


    @Override
    public void setValue(Double value) throws IllegalArgumentException {
        if(relative != null && relative && (value < 0 || value > 100))
            throw new IllegalArgumentException("The relative value of the paramenter must be between 0 and 100");

        super.setValue(value);
    }


    @Override
    protected void setParamValue(String value) throws IllegalArgumentException {
        int index = value.indexOf("%");
        if(index > 0){
            value = value.substring(0, index);
            setRelative(true);
        }

        setValue(new Double(value));
    }


    @Override
    protected String getParamValue() {
        if(getRelative() != null && !getRelative())
            return ""+getValue().intValue()+"%";
        else
            return ""+ getValue().intValue();
    }


    public void setRelative(Boolean relative) throws IllegalArgumentException {
        if(getName().equals(NCLAttributes.ZINDEX))
            throw new IllegalArgumentException("This parameter type can not have a relative valur.");
        
        this.relative = relative;
    }


    public Boolean getRelative() {
        return relative;
    }
}
