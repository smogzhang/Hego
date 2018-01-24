war 包与 war包依赖，需要将被依赖war包打成jar：
<!-- 配置该项目为被依赖的war -->
			<plugin>
				<artifactId>maven-war-plugin</artifactId>
				<configuration>
					<!-- 把class打包jar作为附件 -->
					<attachClasses>true</attachClasses>
					<!-- 将项目的类文件打成jar放到lib目录中 <archiveClasses>true</archiveClasses> -->
				</configuration>
			</plugin>
依赖war项目：
<!-- 该war依赖另一个war -->
		<dependency>
			<groupId>cn.ego</groupId>
			<artifactId>ego-rest</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<type>war</type>
		</dependency>
		<dependency>
			<groupId>cn.ego</groupId>
			<artifactId>ego-rest</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<type>jar</type>
			<classifier>classes</classifier>
			<scope>provided</scope>
		</dependency>