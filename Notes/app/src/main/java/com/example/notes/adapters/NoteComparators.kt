package com.example.notes.adapters

import com.example.notes.models.Note

class NoteComparators {
    class TitleComparator : Comparator<Note>{
        override fun compare(o1: Note?, o2: Note?): Int {
            return o1?.title!!.compareTo(o2?.title!!)
        }
    }

    class DateChangeComparator : Comparator<Note> {
        override fun compare(o1: Note?, o2: Note?): Int {
            return - o1?.change_date_stamp!!.compareTo(o2?.change_date_stamp!!)
        }
    }

    class IdComparator : Comparator<Note> {
        override fun compare(o1: Note?, o2: Note?): Int {
            return - o1?.noteId!!.compareTo(o2?.noteId!!)
        }
    }
}