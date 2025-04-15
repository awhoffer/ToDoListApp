package com.example.todolist.repository;

/// Wrapper Event class is used to wrap content so that the content can be checked if it was handled already.
public class WrapperEvent<T> {
    T mContent;
    Boolean isContentHandled = false;

    /**
     * Creates an instance of WrapperEvent
     *
     * @param pContent - the content that will be held in the WrapperEvent
     */
    public WrapperEvent(T pContent) {
        this.mContent = pContent;
    }

    /**
     * Checks if content was already handled
     *
     * @return true if the content is not handled or false if the content was handled
     */
    public Boolean contentIsNotHandled() {
        return isContentHandled == Boolean.FALSE;
    }

    /**
     * Gets the content held in Wrapper Object
     *
     * @return content
     */
    public T getContent() {

        isContentHandled = true;
        return mContent;

    }

}
