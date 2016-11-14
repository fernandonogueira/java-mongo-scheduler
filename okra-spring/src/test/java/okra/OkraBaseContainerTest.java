/*
 *  Copyright (c) 2016 Fernando Nogueira
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package okra;

import com.mongodb.MongoClient;
import okra.base.Okra;
import okra.builder.OkraSpringBuilder;
import okra.model.DefaultOkraItem;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.GenericContainer;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public abstract class OkraBaseContainerTest {

    @ClassRule
    public static GenericContainer mongo32Container =
            new GenericContainer("mongo:3.2")
                    .withExposedPorts(27017);

    @ClassRule
    public static GenericContainer mongo34Container =
            new GenericContainer("mongo:3.4")
                    .withExposedPorts(27017);

    static Okra<DefaultOkraItem> okraSpringMongo32;
    static Okra<DefaultOkraItem> okraSpringMongo34;

    @BeforeClass
    public static void init() throws UnknownHostException {
        okraSpringMongo32 = prepareDefaultMongo32OkraSpring();
        okraSpringMongo34 = prepareDefaultMongo34OkraSpring();
    }

    public static Okra<DefaultOkraItem> prepareDefaultMongo32OkraSpring() throws UnknownHostException {
        MongoClient client = new MongoClient(
                mongo32Container.getContainerIpAddress(),
                mongo32Container.getMappedPort(27017));

        return new OkraSpringBuilder<DefaultOkraItem>()
                .withMongoTemplate(new MongoTemplate(client, "okraBenchmark"))
                .withDatabase("okraBenchmark")
                .withSchedulerCollectionName("schedulerCollection")
                .withExpiration(5, TimeUnit.MINUTES)
                .withScheduledItemClass(DefaultOkraItem.class)
                .build();

    }

    public static Okra<DefaultOkraItem> prepareDefaultMongo34OkraSpring() throws UnknownHostException {
        MongoClient client = new MongoClient(
                mongo34Container.getContainerIpAddress(),
                mongo34Container.getMappedPort(27017));

        return new OkraSpringBuilder<DefaultOkraItem>()
                .withMongoTemplate(new MongoTemplate(client, "okraBenchmark"))
                .withDatabase("okraBenchmark")
                .withSchedulerCollectionName("schedulerCollection")
                .withExpiration(5, TimeUnit.MINUTES)
                .withScheduledItemClass(DefaultOkraItem.class)
                .build();

    }

    public Okra<DefaultOkraItem> getDefaultOkra() {
        return okraSpringMongo32;
    }

}
