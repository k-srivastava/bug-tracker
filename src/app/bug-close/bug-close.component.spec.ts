import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BugCloseComponent } from './bug-close.component';

describe('BugCloseComponent', () => {
  let component: BugCloseComponent;
  let fixture: ComponentFixture<BugCloseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BugCloseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BugCloseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
