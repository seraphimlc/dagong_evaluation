import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuchang on 16/1/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:base/all.xml")
public class TestDagong {
    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext wac;

//    @Autowired
//    private JobClient jobClient;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

//   @Test
//    public void test(){
//       System.out.println("jobClient = " + jobClient);
//    }
//
//    @Test
//    public void testSearchJob() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/job/forUser.do").cookie(new Cookie("user", "123")))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print()).andReturn();
//
//    }


}
