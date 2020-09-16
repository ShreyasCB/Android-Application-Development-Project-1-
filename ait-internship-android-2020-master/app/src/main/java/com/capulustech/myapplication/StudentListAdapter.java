package com.capulustech.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>
{
    private final LayoutInflater mInflater;
    List<Student> students;
    Context context;

    public StudentListAdapter(Context mContext, List<Student> mStudents)
    {
        context = mContext;
        mInflater = LayoutInflater.from(context);
        students = mStudents;
    }


    class StudentViewHolder extends RecyclerView.ViewHolder
    {
        TextView studentNameTV, usnTV, branchTV, sectionTV, mobileTV;
        CardView cardView;
        ImageView deleteIV;

        public StudentViewHolder(@NonNull View itemView)
        {
            super(itemView);
            studentNameTV = itemView.findViewById(R.id.studentNameTV);
            branchTV = itemView.findViewById(R.id.branchTV);
            sectionTV = itemView.findViewById(R.id.sectionTV);
            mobileTV = itemView.findViewById(R.id.mobileTV);
            usnTV = itemView.findViewById(R.id.usnTV);
            cardView = itemView.findViewById(R.id.cardView);
            deleteIV = itemView.findViewById(R.id.deleteIV);
        }
    }

    @NonNull
    @Override
    public StudentListAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.student_list_item,
                parent, false);
        return new StudentViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position)
    {
        final Student student = students.get(position);
        holder.studentNameTV.setText(student.name);
        holder.usnTV.setText(student.usn);
        holder.branchTV.setText(student.branch);
        holder.sectionTV.setText(student.section);
        holder.mobileTV.setText(student.mobileNumber);

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(context, student.name + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Student.deleteStudent(context, student);
                Toast.makeText(context, student.name + " deleted", Toast.LENGTH_LONG).show();

                ((StudentListActivity) context).recreate();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return students.size();
    }
}