/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.framework.hsven.mybatis.name;

import org.framework.hsven.datasource.util.DataSourceNameGenerator;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.util.ClassUtils;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class MybatisBeanNameGenerator extends DefaultBeanNameGenerator {
	private boolean isDefaultDataSource;
	private String dbName;

	public MybatisBeanNameGenerator(boolean isDefaultDataSource, String dbName) {
		this.isDefaultDataSource = isDefaultDataSource;
		this.dbName = dbName;
	}

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanClassName = definition.getBeanClassName();
		if (beanClassName == null) {
			throw new BeanDefinitionStoreException("MybatisBeanNameGenerator Unnamed bean definition specifies neither " +
					"'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
		}

		String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
		String name = String.format("%s%s", dbName, shortClassName);
		//TODO 此处的生成方式与 org.framework.hsven.datasource.util.DataSourceNameGenerator 中的生成方式一致
		return name;
		/**
		return Introspector.decapitalize(shortClassName);

		if (isDefaultDataSource || StringUtils.isEmpty(dbName)) {
			return super.generateBeanName(definition, registry);
		} else {
			String name = super.generateBeanName(definition, registry);
			return String.format("%s%s", dbName, toUpperCaseFirstOne(name));
		}
		 **/
	}

	private String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
