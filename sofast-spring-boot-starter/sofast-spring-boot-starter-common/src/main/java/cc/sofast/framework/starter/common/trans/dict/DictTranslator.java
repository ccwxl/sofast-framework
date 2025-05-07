package cc.sofast.framework.starter.common.trans.dict;

import cc.sofast.framework.starter.common.trans.Translator;

import java.io.Serializable;

/**
 * @author wxl
 */
public class DictTranslator<T extends Serializable> implements Translator<T, TransDict> {

    private final DictTranslatorService dictTranslator;

    public DictTranslator(DictTranslatorService dictTranslator) {
        this.dictTranslator = dictTranslator;
    }

    @Override
    public String translator(T refVal, TransDict annotation) {

        return dictTranslator.getDict(annotation.group(), refVal.toString());
    }
}
