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
import okra.exception.OkraRuntimeException;
import okra.model.DefaultOkraItem;
import org.junit.Test;

public class ScheduleTest extends OkraBaseContainerTest {

    @Test(expected = OkraRuntimeException.class)
    public void shouldNotScheduleIfRunDateIsNull() {
        DefaultOkraItem item = new DefaultOkraItem();
        scheduleWithOkra(okraSpringMongo32, item);
        item = new DefaultOkraItem();
        scheduleWithOkra(okraSpringMongo34, item);
    }

    @Test(expected = OkraRuntimeException.class)
    public void shouldNotScheduleIfIdIsNotNull() {
        DefaultOkraItem item = new DefaultOkraItem();
        item.setId("123456");
        scheduleWithOkra(okraSpringMongo32, item);

        item = new DefaultOkraItem();
        item.setId("123456");
        scheduleWithOkra(okraSpringMongo34, item);
    }

    private void scheduleWithOkra(Okra<DefaultOkraItem> okra, DefaultOkraItem item) {
        okra.schedule(item);
    }

}
