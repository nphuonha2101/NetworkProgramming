package stManagementIO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class StManagementIO {
    private String filePath;
    private File f;

    public StManagementIO(String filePath) {
        this.filePath = filePath;
        f = new File(filePath);
    }

    public void save (List<Student> stList) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        long[] headerPos = new long[stList.size()];

        raf.writeInt(stList.size());
        // write header
        for (int i = 0; i < stList.size(); i++) {
            long currentPos = raf.getFilePointer();
            headerPos[i] = currentPos;

            raf.writeLong(i);
        }

        // write data
        for (int i = 0; i < stList.size(); i++) {
            Student st = stList.get(i);

            long currentPos = raf.getFilePointer();

            // seek to header pos to write position of data
            raf.seek(headerPos[i]);
            raf.writeLong(currentPos);

            // seek to data pos to write data
            raf.seek(currentPos);

            raf.writeInt(st.getId());
            raf.writeUTF(st.getName());
            raf.writeInt(st.getbYear());
            raf.writeDouble(st.getGrade());
        }

        raf.close();
    }

    public Student get(int index) throws IOException {
        Student st = null;
        RandomAccessFile raf = new RandomAccessFile(f, "r");

        long[] stDataPos = readHeader(raf);

        // get data
        long stPos = stDataPos[index];
        raf.seek(stPos);

        int stId = raf.readInt();
        String stName = raf.readUTF();
        int stBYear = raf.readInt();
        double grade = raf.readDouble();

        raf.close();

        st = new Student(stId, stName, stBYear, grade);
        return st;
    }

    public void setStudent(int index, Student st) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        // positions of data
        long[] stDataPos = readHeader(raf);
        // seek to specific data
        raf.seek(stDataPos[index]);
        // write data
        raf.writeInt(st.getId());
        raf.writeUTF(st.getName());
        raf.writeInt(st.getbYear());
        raf.writeDouble(st.getGrade());

        raf.close();
    }

    private long[] readHeader(RandomAccessFile raf) throws IOException {
        int stListSize = raf.readInt();
        long[] stDataPos = new long[stListSize];

        // get header
        for (int i = 0; i < stListSize; i++)
            stDataPos[i] = raf.readLong();

        return stDataPos;
    }

    public static void main(String[] args) throws IOException {
        Student st1 = new Student(1, "Nguyễn Văn A", 2003, 9.2);
        Student st2 = new Student(2, "Nguyễn Văn B", 2003, 8.56);
        Student st3 = new Student(3, "Nguyễn Thị E", 2003, 9.4);
        Student st4 = new Student(4, "Nguyễn Thị D", 2003, 8.5);

        List<Student> studentList = new ArrayList<>();
        studentList.add(st1);
        studentList.add(st2);
        studentList.add(st3);

        String filePath = "C:\\Users\\NhaNguyen\\Desktop\\test\\data.dat";
        StManagementIO stManagementIO = new StManagementIO(filePath);

        stManagementIO.save(studentList);
        Student st = stManagementIO.get(2);
        System.out.println(st);

        stManagementIO.setStudent(1, st4);
        st = stManagementIO.get(1);
        System.out.println(st);

    }

}
