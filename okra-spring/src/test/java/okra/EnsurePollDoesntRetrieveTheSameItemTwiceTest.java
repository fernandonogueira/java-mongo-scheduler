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

import okra.base.Okra;
import okra.model.DefaultOkraItem;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EnsurePollDoesntRetrieveTheSameItemTwiceTest extends OkraBaseContainerTest {

    private static Okra<DefaultOkraItem> scheduler;

    @BeforeClass
    public static void init() throws UnknownHostException {
        scheduler = prepareDefaultScheduler();
    }

    @Test
    public void ensurePollDoesntRetrieveTheSameItemTwiceTest() {
        given_that_an_item_was_scheduled();

        Optional<DefaultOkraItem> retrievedOpt = scheduler.poll();

        assertThat(retrievedOpt.isPresent()).isTrue();

        DefaultOkraItem item = retrievedOpt.get();

        Optional<DefaultOkraItem> optThatShouldBeEmpty = scheduler.poll();

        assertThat(optThatShouldBeEmpty.isPresent()).isFalse();

        // Then... Delete acquired item
        scheduler.delete(item);
    }

    private void given_that_an_item_was_scheduled() {
        DefaultOkraItem item = new DefaultOkraItem();
        item.setRunDate(LocalDateTime.now().minusNanos(100));
        scheduler.schedule(item);
    }

}
