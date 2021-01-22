package org.framework.hsven.utils.view;

import com.fasterxml.jackson.annotation.JsonView;
import org.framework.hsven.utils.JsonUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class JsonViewTest {
    public static void main(String[] args) {
        DaoEntity entity = DaoEntity.of();
        System.out.println(JsonUtils.toJson(entity));
        System.out.println(JsonUtils.toJson(entity, IdView.class));
        System.out.println(JsonUtils.toJson(entity, TextView.class));
        System.out.println(JsonUtils.toJson(entity, DicView.class));
        System.out.println(JsonUtils.toJson(entity, SecrecyView.class));
    }

//    class TestController {
//
//        /**
//         * 扩展： 使用正则表达，对URL传的参数校验。 类似/user/{id:\\d+}，代表要求传的参数是个数字。
//         * <p>
//         * JsonView使用步骤3： 在Controller方法上指定视图
//         * </p>
//         *
//         * @param idxxx
//         * @return
//         */
//        @RequestMapping(value = "/user/{id:\\d+}", method = RequestMethod.GET)
//        @JsonView(TextView.class)
//        public DaoEntity getInfo(@PathVariable(name = "id", required = false) Integer idxxx) {
//            return DaoEntity.of();
//        }
//    }

}
