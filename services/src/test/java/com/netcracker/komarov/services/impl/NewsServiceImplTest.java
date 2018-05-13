package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.NewsService;
import com.netcracker.komarov.services.util.CustomPasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class NewsServiceImplTest {
    @Mock
    @Autowired
    private NewsService newsService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientService clientService;

    @Before
    public void init() {
        PersonDTO dto1 = new PersonDTO(0, "Max", "Ul",
                1, 1, "Pessimist", "password");
        PersonDTO dto2 = new PersonDTO(0, "Pav", "Zar",
                2, 2, "Dancer", "disco");
        clientService.save(dto1);
        clientService.save(dto2);

        PersonDTO dto = new PersonDTO(0, "Max", "UL",
                1, 1, "Pessimist", "password");
        adminService.addAdmin(dto);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO1 = new NewsDTO(0, date, "Gen t1", "b1", NewsStatus.GENERAL.toString());
        NewsDTO newsDTO2 = new NewsDTO(0, date, "Cli t2", "b2", NewsStatus.CLIENT.toString());
        NewsDTO newsDTO3 = new NewsDTO(0, date, "Cli t3", "b3", NewsStatus.CLIENT.toString());
        newsService.addNews(newsDTO1, 1);
        newsService.addNews(newsDTO2, 1);
        newsService.addNews(newsDTO3, 1);

        Collection<Long> clientIds = Stream.of(1L, 2L).collect(Collectors.toList());
        newsService.addClientNews(clientIds, 2);
        newsService.addClientNews(Collections.singleton(2L), 3);
    }

    @Test
    public void getAllNews() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO1 = new NewsDTO(1, date, "Gen t1", "b1", NewsStatus.GENERAL.toString());
        NewsDTO newsDTO2 = new NewsDTO(2, date, "Cli t2", "b2", NewsStatus.CLIENT.toString());
        NewsDTO newsDTO3 = new NewsDTO(3, date, "Cli t3", "b3", NewsStatus.CLIENT.toString());
        Collection<NewsDTO> dtos = Stream.of(newsDTO1, newsDTO2, newsDTO3).collect(Collectors.toList());
        assertEquals(dtos, newsService.getAllNews());
    }

    @Test
    public void getAllNewsByStatus() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO2 = new NewsDTO(2, date, "Cli t2", "b2", NewsStatus.CLIENT.toString());
        NewsDTO newsDTO3 = new NewsDTO(3, date, "Cli t3", "b3", NewsStatus.CLIENT.toString());
        Collection<NewsDTO> dtos = Stream.of(newsDTO2, newsDTO3).collect(Collectors.toList());
        assertEquals(dtos, newsService.getAllNewsByStatus(NewsStatus.CLIENT));
    }


    @Test
    public void getAllClientNewsById() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO2 = new NewsDTO(2, date, "Cli t2", "b2", NewsStatus.CLIENT.toString());
        NewsDTO newsDTO3 = new NewsDTO(3, date, "Cli t3", "b3", NewsStatus.CLIENT.toString());
        Collection<NewsDTO> dtos = Stream.of(newsDTO2, newsDTO3).collect(Collectors.toList());
        assertEquals(dtos, newsService.getAllClientNewsById(2));
    }


    @Test
    public void findById() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO1 = new NewsDTO(1, date, "Gen t1", "b1", NewsStatus.GENERAL.toString());
        assertEquals(newsDTO1, newsService.findById(1));
    }

    @Test
    public void addNews() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO1 = new NewsDTO(4, date, "Gen t4", "b4", NewsStatus.GENERAL.toString());
        assertEquals(newsDTO1, newsService.
                addNews(new NewsDTO(0, date, "Gen t4", "b4", NewsStatus.GENERAL.toString()), 1));
    }

    @Test
    public void addClientNews() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO3 = new NewsDTO(3, date, "Cli t3", "b3", NewsStatus.CLIENT.toString());
        assertEquals(newsDTO3, newsService.addClientNews(Collections.singleton(1L), 3));
    }

    @Test
    public void update() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
        String date = simpleDateFormat.format(new Date());
        NewsDTO newsDTO2 = new NewsDTO(2, date, "Client News", "News for all clients",
                NewsStatus.CLIENT.toString());
        NewsDTO res = new NewsDTO(2, date, "Client News", "News for all clients",
                NewsStatus.CLIENT.toString());
        assertEquals(res, newsService.update(newsDTO2));
    }
}