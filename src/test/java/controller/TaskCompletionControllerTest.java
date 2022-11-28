package controller;

import com.backend.QuestPetsApplication;
import com.backend.controller.TaskCompletionController;
import com.backend.entities.IDs.SessionID;
import com.backend.usecases.AccountManager;
import com.backend.usecases.TaskManager;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(classes = QuestPetsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskCompletionControllerTest {

    @SuppressWarnings("unused")
    @Autowired
    TaskCompletionController completionController;
    private SessionID sessionID;
    private final String task = "attend lecture";
    private final String image = "https://www.saycampuslife.com/wp-content/uploads/2017/10/collegelectures-800x500_c.jpg";
    private final double reward = 100;

    @BeforeEach
    public void setup() {
        String username = "username";
        String password = "abc123!";
        sessionID = new SessionID((String) ((JSONObject) Objects.requireNonNull(AccountManager.loginAccount(username, password).getBody())).get("sessionID"));
    }

    @AfterEach
    public void tearDown() {
        TaskManager.deleteAllCorrelatedCompletions(AccountManager.verifySession(sessionID));
        AccountManager.logoutAccount(sessionID);
    }

    @Test
    public void postCompletedTaskTest() {
        //value
        HttpStatus expectedStatus = HttpStatus.OK;

        //action
        ResponseEntity<?> actualResponse = completionController.postCompletedTask(
                sessionID.toString(), task, image, reward
        );

        //assertion message
        String completeTaskMessage = "Unable finish task due to invalid session";

        //assertion statement
        Assertions.assertEquals(expectedStatus, actualResponse.getStatusCode(), completeTaskMessage);
    }

    @Test
    public void postCompletedTaskInvalidSessionTest() {
        //value
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        //action
        ResponseEntity<?> actualResponse = completionController.postCompletedTask(
                "bad id", task, image, reward
        );

        //assertion message
        String completeTaskMessage = "Unable finish task due to invalid session";

        //assertion statement
        Assertions.assertEquals(expectedStatus, actualResponse.getStatusCode(), completeTaskMessage);
    }

    @Test
    public void postCompletedTaskAlreadyCompleteTest() {
        //value
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

        //action
        completionController.postCompletedTask(
                sessionID.toString(), task, image, reward
        );
        ResponseEntity<?> actualResponse = completionController.postCompletedTask(
                sessionID.toString(), task, image, reward
        );

        //assertion message
        String completeTaskMessage = "Unable finish task as it is already completed";

        //assertion statement
        Assertions.assertEquals(expectedStatus, actualResponse.getStatusCode(), completeTaskMessage);
    }
}