import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RegPerson r = new RegPerson();

        System.out.println();
        System.out.println("           _______");
        System.out.println("           WELCOME");
        System.out.println("           -------");

        boolean kjor = true;
        while(kjor) {
            System.out.println();
            System.out.println("_________________________________________________________");
            System.out.println("To print a person type p");
            System.out.println("To quit type q");
            System.out.println("To add a person type a");
            System.out.println("To remove a person type r");
            System.out.println("To see people count type c");
            System.out.println("To see the oldes or the youngest person type oy");
            System.out.println("To see if a person is the oldest or the youngest type coy");
            System.out.println("To write it to a text file type w");
            System.out.println("To read a text file type rf");
            System.out.println("To clear the terminal type cln");
            System.out.println("To see files in the same folder type fi");
            System.out.println("---------------------------------------------------------");

            System.out.println();
            Scanner s = new Scanner(System.in);
            System.out.println("Answer: ");
            String input = s.nextLine();

            if(input.equals("p")) {
                if(r.linkedList.isEmpty()) {
                    System.out.println("The person register is empty.");
                    System.out.println();
                } else {
                    System.out.println("Id for the person: ");
                    int intInput = s.nextInt();
                    r.linkedList.printPerson(intInput);
                }
            } else if(input.equals("q")) {
                kjor = false;
            } else if(input.equals("cls")) {
                // Her skal den kjøre cls på cmd, hvis bruker kjører Windows. Hvis ikke er det Unix(clear).
            } else if(input.equals("fi")) {
                // Her skal den kjøre dir på cmd, hvis bruker kjører Windows. Hvis ikke er det Unix(ls)
            } else if(input.equals("a")) {
                System.out.println("Name: ");
                String inpA1 = s.nextLine();
                System.out.println("Surname: ");
                String inpA2 = s.nextLine();
                System.out.println("Age: ");
                int inpA3 = s.nextInt();

                if(inpA1.equals("") || inpA2.equals("") || inpA2.equals("")) {
                    System.out.println("Failed, you must have name, surname and age!");
                } else {
                    System.out.println("Added person with id " + r.add(inpA1, inpA2, inpA3) + ".");
                    System.out.println();
                }
            } else if(input.equals("r")) {
                System.out.println();
                System.out.println("Id for the person: ");
                int inpR = s.nextInt();
                if(!r.checkID(inpR)) { // sjekk om denne kan feile etterpaa.
                    System.out.println("There is no person in this register, with the ID you have given.");
                } else {
                    r.linkedList.removePerson(inpR);
                    r.removeID(inpR);
                }
            } else if(input.equals("c")) {
                System.out.println();
                System.out.println("Count: " + r.linkedList.nodeCounter());
            } else if(input.equals("w")) {
                String inpF = s.nextLine();
                try {
                    r.writeFile(inpF);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(input.equals("rf")) {
                String inpRF = s.nextLine();
                try {
                    r.readFile(inpRF);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(input.equals("oy")) {
                System.out.println("For youngest type y and for oldest type o");
                String inpOY = s.nextLine();

                if(r.linkedList.isEmpty()) {
                    System.out.println();
                    System.out.println("No person in this register.");
                } else if(inpOY.equals("y") && r.linkedList.nodeCounter() == 1) {
                    System.out.println();
                    System.out.println("There is only one person in this register.");
                } else if(inpOY.equals("o") && r.linkedList.nodeCounter() == 1) {
                    System.out.println();
                    System.out.println("There is only one person in this register.");
                } else if(inpOY.equals("y")) {
                    System.out.println();
                    System.out.println(r.linkedList.startNode.data);
                } else if(inpOY.equals("o") && r.linkedList.endNode != null) {
                    System.out.println();
                    System.out.println(r.linkedList.endNode.data);
                } else {
                    System.out.println();
                    System.out.println("Invalid answer, try again.");
                }
            } else if(input.equals("coy")) {
                int inpCoy = s.nextInt();
                r.linkedList.checkIfYoungestOrOldest(inpCoy);
            } else {
                System.out.println();
                System.out.println("Invalid answer, try again.");
            }
        }
    }
}

class Person {

    int idI = 0;
    String name;
    String surname;
    int age;
    int id;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.id = idI;
        idI++;
    }

    /*
    Prints the person information
    to the terminal
    */

    public String toString() {
        return "Name: " + name + '\n' + "Surname: " + surname + '\n' + "Age: " + age;
    }

    /*
    Used to compare age,
    for the sort function in LinkedList.py.
     */
    public int checkAge(Person person) {
        if (this.age > person.age) {
            return 1;
        } else {
            return -1;
        }
    }
}

class Node {

    Person data;
    Node next;
    Node previous;

    public Node(Person data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }
}

class LinkedList {

    Node startNode;
    Node endNode;

    public LinkedList() {
        this.startNode = null;
        this.endNode = null;
    }

    /*
    Checks if the startNode is None,
    if it is then the linkedlist is empty.
    */

    public boolean isEmpty() {
        if (startNode == null) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Add function which is sorted by age,
    from youngest to the eldest.
    */

    public void add(Person person) {
        Node node = new Node(person);

        if (this.isEmpty()) {
            startNode = node;
        } else if (startNode.data.checkAge(node.data) >= 0) {
            node.next = startNode;
            startNode.previous = node;
            startNode = node;
        } else {
            Node now = startNode;
            boolean found = false;
            while (now.next != null) {
                if (now.next.data.checkAge(node.data) >= 0) {
                    node.next = now.next;
                    now.next = node;
                    node.previous = now;
                    node.next.previous = node;
                    endNode = node.next;
                    found = true;
                    break;
                }

                now = now.next;
            }

            if (!found) {
                now.next = node;
                node.previous = now;
                endNode = node;
            }
        }

    }

    /*
    Counts nodes in the linkedlist.
    */

    public int nodeCounter() {
        Node sNode = startNode;
        int count = 0;

        while(sNode != null) {
            count++;
            sNode = sNode.next;
        }

        return count;
    }

    /*
    Prints out the person objects
    in the linkedlist, by using the person ID.
    */

    public void printPerson(int id) {
        if (this.isEmpty()) {
            System.out.println();
            System.out.println("The person register is empty.");

            Node iterNode = startNode;

            boolean printBool = false;
            while(iterNode != null) {
                if(iterNode.data.id == id) {
                    System.out.println();
                    System.out.println(iterNode.data);
                    printBool = true;
                    iterNode = iterNode.next;

                    if (printBool == false) {
                        System.out.println("There is no person in this register, with the ID you have given.");
                    }

                }
            }

        }
    }

    /*
    Remove a person, by using person ID
    */

    public void removePerson(int id) {
        if(isEmpty()) {
            System.out.println();
            System.out.println("The person register is empty");
        } else if(startNode.data.id == id) {
            startNode = null;
            System.out.println();
            System.out.println("Person removed.");
        } else if(startNode.next.next == null && startNode.next.data.id == id) {
            startNode.next = null;
            System.out.println();
            System.out.println("Person removed.");
        } else if(endNode.data.id == id) {
            endNode = endNode.previous;
            endNode.next.previous = null;
            endNode.next = null;
        }

        Node iterNode = startNode;
        while(iterNode != null) {
            if(id == iterNode.data.id) {
                iterNode.previous.next = iterNode.next;
                iterNode.next.previous = iterNode.previous.next;
                iterNode.previous = null;
                iterNode.next = null;
                System.out.println();
                System.out.println("Person removed.");
            }

            iterNode = iterNode.next;
        }
    }

    /*
    Check if a person is the youngest
    or the oldest.
     */

    public void checkIfYoungestOrOldest(int id) {
        if(isEmpty()) {
            System.out.println();
            System.out.print("The person register is empty.");
        } else if(nodeCounter() == 1) {
            System.out.println("There is only one person in the register.");
        } else if(startNode != null && endNode != null && id == startNode.data.id) {
            System.out.println();
            System.out.println("This person is the youngest.");
        } else if(endNode != null && id == endNode.data.id) {
            System.out.println();
            System.out.println("The person is the oldest.");
        } else {
            System.out.println();
            System.out.println("This person is not the youngest or the oldest.");
        }
    }
}

class RegPerson {

    LinkedList linkedList;
    ArrayList<Integer> addedId;
    public RegPerson() {
        this.linkedList = new LinkedList();
        this.addedId = new ArrayList<>();
    }

    /*
    Used for adding the person object,
    into the linkedlist.
     */

    public int add(String name, String surname, int age) {
        Person person = new Person(name, surname, age);
        addedId.add(person.id);
        linkedList.add(person);

        return person.id;
    }

    /*
    Checks if the ID is in the person register.
     */

    public boolean checkID(int idInput) {
        for (Integer id : addedId) {
            if (id == idInput) {
                return true;
            }
        }
        return false;
    }

    /*
    Removes the ID from the addedID array,
    when removing the person from
    the doubly linked list in LinkedList.py.
     */

    public void removeID(int idInput) {
        for(Integer id : addedId) {
            if(id == idInput) {
                addedId.remove(id);
            }
        }
    }

    /*
    Reads a text file (.txt) and add
    the people to the linkedlist.
     */

    public void readFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));

        if(file.length() == 0) {
            System.out.println("The file is empty");
        } else {
            String person;
            person = br.readLine();
            String[] ps;
            ps = person.split(" ");
            this.add(ps[0], ps[1], Integer.parseInt(ps[2]));
        }
    }

    /*
    Writes the person objects
    from the linkedlist, to a text file (.txt).
     */
    public void writeFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter (new FileWriter(filename));

        Node sNode = linkedList.startNode;
        while (sNode != null) {
            writer.write(sNode.data.name + " " + sNode.data.surname + ' ' + sNode.data.age);
            writer.write('\n');
            sNode = sNode.next;
        }

        writer.close();
    }
}