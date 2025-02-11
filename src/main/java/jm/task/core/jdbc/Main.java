package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl(new UserDaoJDBCImpl());

//        создали таблицу
        service.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем — name добавлен в базу данных)
        service.saveUser("Иван", "Иванов", (byte) 30);
        service.saveUser("Николай", "Николаев", (byte) 46);
        service.saveUser("Пётр", "Петров", (byte) 25);
        service.saveUser("Сидор", "Сидоров", (byte) 40);

//        Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        service.getAllUsers();

            //удаление записи с id=2
//        service.removeUserById(2);


//        Очистка таблицы User(ов)
//        service.cleanUsersTable();

//        удалили таблицу
//        service.dropUsersTable();

//
    }
}
