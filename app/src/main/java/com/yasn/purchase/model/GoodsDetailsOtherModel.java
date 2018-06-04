package com.yasn.purchase.model;

import java.io.Serializable;

public class GoodsDetailsOtherModel implements Serializable {

    /**
     * goodsIntro : {"intro":"<p>本品是一种汽车专用制冷剂，具有优异的制冷效果，可保护制冷系统有效制冷；今年从技术上全面升级，使之安全性高，出度更高达99.99%；外观更在铝罐一体成型下，精美耐久，本品为R-134a，请务必于车内原制冷剂类别相符。<\/p><p><img src=\"http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//22549528.jpg\" style=\"\" title=\"show.jpg\"/><\/p><p><img src=\"http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//28032214.jpg\" title=\"show.jpg\" alt=\"show.jpg\"/><\/p><p><img src=\"http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//23225590.jpg\" title=\"show.jpg\"/><\/p><p><img src=\"http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//23281811.jpg\" style=\"\" title=\"show.jpg\"/><\/p><p><img src=\"http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//23540524.jpg\" style=\"\" title=\"show.jpg\"/><\/p><p><img src=\"http://caigou.yasn.com/static/attachment/ueditor/2017/3/28/14//15117066.jpg\" title=\"show.jpg\" alt=\"show.jpg\"/><\/p>","successCase":"<p>测试11111<\/p>","isSuccessCase":1}
     */

    private GoodsIntroBean goodsIntro;

    public GoodsIntroBean getGoodsIntro() {
        return goodsIntro;
    }

    public void setGoodsIntro(GoodsIntroBean goodsIntro) {
        this.goodsIntro = goodsIntro;
    }

    public static class GoodsIntroBean {
        /**
         * intro : <p>本品是一种汽车专用制冷剂，具有优异的制冷效果，可保护制冷系统有效制冷；今年从技术上全面升级，使之安全性高，出度更高达99.99%；外观更在铝罐一体成型下，精美耐久，本品为R-134a，请务必于车内原制冷剂类别相符。</p><p><img src="http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//22549528.jpg" style="" title="show.jpg"/></p><p><img src="http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//28032214.jpg" title="show.jpg" alt="show.jpg"/></p><p><img src="http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//23225590.jpg" title="show.jpg"/></p><p><img src="http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//23281811.jpg" style="" title="show.jpg"/></p><p><img src="http://caigou.yasn.com/static/attachment/ueditor/2017/5/15/11//23540524.jpg" style="" title="show.jpg"/></p><p><img src="http://caigou.yasn.com/static/attachment/ueditor/2017/3/28/14//15117066.jpg" title="show.jpg" alt="show.jpg"/></p>
         * successCase : <p>测试11111</p>
         * isSuccessCase : 1
         */

        private String intro;
        private String successCase;
        private int isSuccessCase;

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getSuccessCase() {
            return successCase;
        }

        public void setSuccessCase(String successCase) {
            this.successCase = successCase;
        }

        public int getIsSuccessCase() {
            return isSuccessCase;
        }

        public void setIsSuccessCase(int isSuccessCase) {
            this.isSuccessCase = isSuccessCase;
        }
    }
}
