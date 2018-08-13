/**
 * @FileName: ReplaceAtomic.java
 * @Package: com.asura.framework.generator.replace
 * @author youshipeng
 * @created 2017/2/22 16:41
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.replace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.asura.framework.generator.replace.Constants.*;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @since 1.0
 * @version 1.0
 */
public class ReplaceRepository {

    private List<ReplaceAtom> replaceAtoms;

    public ReplaceRepository() {
        replaceAtoms = new ArrayList<>();
        add(DEFAULT_REPLACE_ATOM_DATE_TIME, DATE_TIME_FORMAT.format(new Date()));
        add(DEFAULT_REPLACE_ATOM_DATE, DATE_FORMAT.format(new Date()));
        add(DEFAULT_REPLACE_ATOM_YEAR, YEAR_FORMAT.format(new Date()));
    }

    public List<ReplaceAtom> getReplaceAtoms() {
        return replaceAtoms;
    }

    public void add(String target, String value) {
        replaceAtoms.add(new ReplaceAtom(target, value));
    }

    public ReplaceAtom get(String target) {
        for (ReplaceAtom atom: replaceAtoms) {
            if (target.equals(atom.getTarget())) {
                return atom;
            }
        }
        return null;
    }

    class ReplaceAtom {
        private String target;
        private String value;

        ReplaceAtom(String target, String value) {
            this.target = target;
            this.value = value;
        }

        String getValue() {
            return value;
        }

        String getTarget() {
            return target;
        }
    }

}