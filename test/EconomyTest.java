import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class EconomyTest {

    @Test
    public void testExpectedYearlyIncome() {
        // Arrange
        ArrayList<Member> dummyMembers = new ArrayList<>();

        Member m1 = new Member();
        m1.setMemberPrice(1000);
        Member m2 = new Member();
        m2.setMemberPrice(1600);
        Member m3 = new Member();
        m3.setMemberPrice(500);

        dummyMembers.add(m1);
        dummyMembers.add(m2);
        dummyMembers.add(m3);

        // Erstat den rigtige medlemsliste midlertidigt
        MemberController.MemberList = dummyMembers;

        // Act
        int faktiskIndkomst = Economy.getExpectedYearlyIncome();

        // Assert
        assertEquals(3100, faktiskIndkomst, "Summen af 1000 + 1600 + 500 skal være 3100");
    }

    @Test
    public void testIncomeWithNoMembers() {
        MemberController.MemberList = new ArrayList<>();
        int income = Economy.getExpectedYearlyIncome();
        assertEquals(0, income);
    }

}
